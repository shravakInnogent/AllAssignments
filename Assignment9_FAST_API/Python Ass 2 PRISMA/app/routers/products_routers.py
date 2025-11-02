from fastapi import APIRouter, HTTPException, status
from typing import List, Optional
from app.schemas.product import ProductCreate, ProductResponse, ProductUpdate
from app.database import prisma
from app.utils.logger import logger


router = APIRouter(
    prefix="/api/products",
    tags=["Products"]
    )

@router.post("/", status_code=status.HTTP_201_CREATED)
async def create_product(product: ProductCreate):
    logger.info(f"Adding Product...")
    existing = await prisma.product.find_first(
        where={
            "name": product.name,
            "companyId": product.companyId
        }
    )
    
    if existing:
        logger.warning(f"Product {product.name} Already Exists With Company {product.company_id} ")
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"Product '{product.name}' already exists for this company"
        )
    
    new_product = await prisma.product.create(
        data={
            "name": product.name,
            "description": product.description,
            "price": product.price,
            "stock": product.stock,
            "companyId": product.companyId,
            "categoryId": product.categoryId
        }
    )
    logger.info(f"Product Added Successfully.")   
    return f"Added Successfully!"

@router.get("/get", response_model=List[ProductResponse])
async def get_all_products():
    products = await prisma.product.find_many()
    if not products:
        logger.warning(f"No Product Found.")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Product Found"
        )
    
    return products

# READ ONE
@router.get("/{product_id}", response_model=ProductResponse)
async def get_product(product_id: int):
    logger.info(f"Getting Products ")
    product = await prisma.product.find_unique(where={"id": product_id})
    
    if not product:
        logger.warning(f"No Product Found.")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Product with ID {product_id} not found"
        )
    
    return product

@router.put("/{product_id}", response_model=ProductResponse)
async def update_product(product_id: int, product_update: ProductUpdate):
    logger.info(f"Updating Product")
    existing = await prisma.product.find_unique(where={"id": product_id})
    if not existing:
        logger.warning(f"Product not found: ID {product_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Product with ID {product_id} not found"
        )
    
    update_data = product_update.model_dump(exclude_unset=True)
    
    updated_product = await prisma.product.update(
        where={"id": product_id},
        data=update_data
    )
    logger.info(f"Product Updated")
    return updated_product

@router.delete("/{product_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_product(product_id: int):
    logger.info(f"Deleting Product")
    product = await prisma.product.find_unique(where={"id": product_id})
    
    if not product:
        logger.warning(f"Product not found: ID {product_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Product with ID {product_id} not found"
        )
    await prisma.product.delete(where={"id": product_id})
    logger.info(f"Product ID: {product_id} Deleted")
    return None
