from fastapi import APIRouter, HTTPException, status
from typing import List
from app.schemas.category import CategoryCreate, CategoryResponse, CategoryUpdate
from app.database import prisma
from app.utils.logger import logger

router = APIRouter(prefix="/api/categories", tags=["Categories"])

@router.post("/", response_model=CategoryResponse, status_code=status.HTTP_201_CREATED)
async def create_category(category: CategoryCreate):
    logger.info(f"Creating Category...")
    existing = await prisma.category.find_unique(where={"name": category.name})
    if existing:
        logger.warning(f"Duplicate Category")
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"Category '{category.name}' already exists"
        )
    
    new_category = await prisma.category.create(
        data={"name": category.name, "description": category.description}
    )
    logger.info(f"Category Created")
    return new_category

@router.get("/", response_model=List[CategoryResponse])
async def get_all_categories():
    logger.info(f"Getting Categories...")
    categories = await prisma.category.find_many()
    if not categories:
        logger.warning(f"No Category Found")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"No Category found"
        )
    return categories

@router.get("/{category_id}", response_model=CategoryResponse)
async def get_category(category_id: int):
    category = await prisma.category.find_unique(where={"id": category_id})
    if not category:
        logger.warning(f"No Category Found")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Category with ID {category_id} not found"
        )
    return category


@router.put("/{category_id}", response_model=CategoryResponse)
async def update_product(category_id: int, categoty_update: CategoryUpdate):
    logger.info(f"Updating Category")
    existing = await prisma.category.find_unique(where={"id": category_id})
    if not existing:
        logger.warning(f"Category not found: ID {category_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Product with ID {category_id} not found"
        )
    
    update_data = categoty_update.model_dump(exclude_unset=True)
    
    updated_product = await prisma.product.update(
        where={"id": category_id},
        data=update_data
    )
    logger.info(f"Category Updated")
    return updated_product

@router.delete("/{category_id}")
async def delete_category(category_id: int):
    logger.info(f"Deleting Category")
    category = await prisma.category.find_unique(where={"id": category_id})
    if not category:
        logger.warning(f"Category not found: ID {category_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Category with ID {category_id} not found"
        )
    products_count = await prisma.product.count(where={"categoryId": category_id})
    
    if products_count > 0:
        raise HTTPException(
            status_code=400,
            detail=f"Cannot delete category. {products_count} product(s) are using this category. Please delete or reassign those products first."
        )    
    await prisma.category.delete(where={"id": category_id})
    logger.info(f"Category ID: {category_id} Deleted")
    return None