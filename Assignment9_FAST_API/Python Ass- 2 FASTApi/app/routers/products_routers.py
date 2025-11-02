from fastapi import APIRouter, Depends, HTTPException, status, Query
from sqlalchemy.orm import Session
from sqlalchemy import or_
from typing import List, Optional
from ..database import get_db
from ..models import product as product_model
from ..schemas import product as product_schema
from ..models import category as category_model
from ..models import company as company_model
from app.utils.logger import logger

from sqlalchemy import or_
router = APIRouter(
    prefix="/api/products",
    tags=["Products"]
)

@router.post("/", response_model=product_schema.ProductResponse, status_code=status.HTTP_201_CREATED)
def create_product(product: product_schema.ProductCreate, db: Session = Depends(get_db)):
    logger.info(f"Adding Product...")
    existing = db.query(product_model.Product).filter(
        product_model.Product.name == product.name,
        product_model.Product.company_id == product.company_id
    ).first()
    
    if existing:
        logger.warning(f"Product {product.name} Already Exists With Company {product.company_id} ")
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail= f"Product Already Exists"
            )
        
    db_product = product_model.Product(**product.model_dump())
    db.add(db_product)
    db.commit()
    logger.info(f"Product Added Successfully.")   
    db.refresh(db_product)
    
    return db_product

@router.get("/get", response_model= List[product_schema.ProductResponse])
def get_product(db:Session = Depends(get_db)):
    logger.info(f"Getting Products ")
    product = db.query(product_model.Product)
    if not product:
        logger.warning(f"No Product Found.")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Products listed"
        )
    return product

@router.get("/search/{search}", response_model= List[product_schema.ProductResponse])
def get_product(search: str,db:Session = Depends(get_db)):
    # logger.info(f"Searching...")
    category  =db.query(category_model.Category)
    company = db.query(company_model.Company)
    cId = 0 
    company_id =0
    for com in company:
        if com.name == search:
            company_id = com.id    
    for c in category:
        if c.name == search:
            cId = c.id
            
    product = db.query(product_model.Product).filter(
        or_(
        product_model.Product.category_id == cId ,
        product_model.Product.name == search,
        product_model.Product.company_id == company_id
    )
    )
    
    if not product:
        logger.warning(f"No Product Found with Query {search}.")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Products listed"
        )
        
    return product

@router.patch("/update/{update_id}", response_model= product_schema.ProductResponse)
def update_product(update_id,product_update: product_schema.ProductUpdate, db:Session = Depends(get_db)):
    logger.info(f"Updating Product")
    product = db.query(product_model.Product).filter(
        product_model.Product.id == update_id
    ).first()
    
    if not product :
         logger.warning(f"Product not found: ID {update_id}")
         raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Product with ID {update_id} not found"
        )
    
    updated = product_update.model_dump(exclude_unset=True)
    for key, value in updated.items():
        setattr(product, key, value)
    
    db.add(product)
    db.commit()
    db.refresh(product)
    logger.info(f"Product Updated")
    return product

@router.delete("/delete/{delete_id}")
def delete_product(delete_id, db:Session =  Depends(get_db)):
    logger.info(f"Deleting Product")
    product = db.query(product_model.Product).filter(
        product_model.Product.id == delete_id
    ).first()     
    
    if not product :
        logger.warning(f"Product not found: ID {delete_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Product with ID {delete_id} not found"
        )
    
    db.delete(product)
    db.commit()
    logger.info(f"Product ID: {delete_id} Deleted")
    return f"Product of ID: {delete_id}"
