from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List
from ..database import get_db
from ..models import category as category_model
from ..schemas import category as category_schema
from app.utils.logger import logger

router = APIRouter(
    prefix="/api/categories",
    tags=["Categories"]
)

@router.post("/", response_model=category_schema.CategoryResponse, status_code=status.HTTP_201_CREATED)
def create_category(category: category_schema.CategoryCreate, db: Session = Depends(get_db)):
    logger.info(f"Creating Category...")
    existing = db.query(category_model.Category).filter(
        category_model.Category.name == category.name
    ).first()
    
    if existing:
       logger.warning(f"Duplicate Category")
       raise HTTPException(
           status_code=status.HTTP_400_BAD_REQUEST,
           detail=f"Category with ID: {id} Is Already Listed "
       )
       
    db_category = category_model.Category(**category.model_dump())
    db.add(db_category)
    db.commit()
    db.refresh(db_category)
    logger.info(f"Category Created")
    return db_category

@router.get("/get", response_model=List[category_schema.CategoryResponse])
def get_categories(db: Session = Depends(get_db)):
    logger.info(f"Getting Categories...")
    category = db.query(category_model.Category)
    
    if not category:
        logger.warning(f"No Category Found")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Category Availble."
        )
        
    return category

@router.patch("/update/{id}",response_model = category_schema.CategoryResponse)
def update_category(update_id, category_update : category_schema.CategoryUpdate, db: Session = Depends(get_db)):
    logger.info(f"Updating Category")
    category = db.query(category_model.Category).filter(
        category_model.Category.id == update_id
    ).first()
    
    if not category:
        logger.warning(f"No Category Found ID:{id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Category Found With ID:{id}"
        )
        
    updated_category = category_update.model_dump(exclude_unset=True)
    for key, value in updated_category.items():
        setattr(category, key, value)
    db.add(category)
    db.commit()
    db.refresh(category)
    logger.info(f"Category Updated ID: {id}")
    return category

@router.delete("/delete/{id}")
def delete_category(delete_id, db:Session = Depends(get_db)):
    logger.info("Deleting Category")
    category = db.query(category_model.Category).filter(
        category_model.Category.id == delete_id
    ).first()
    
    if not category:
        logger.warning(f"No Category Found, ID :{id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Category Found, ID:{id}"
        )
    
    db.delete(category)
    db.commit()
    return f"Category of ID: {delete_id} is Successfully Deleted."
