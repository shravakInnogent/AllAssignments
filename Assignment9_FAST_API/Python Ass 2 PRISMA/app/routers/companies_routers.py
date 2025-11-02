from fastapi import APIRouter, HTTPException, status, Depends
from typing import List
from app.schemas.company import CompanyCreate, CompanyResponse, CompanyUpdate
from app.database import get_db, prisma
from app.utils.logger import logger

router = APIRouter(
    prefix="/api/companies", 
    tags=["Companies"]
    )

@router.post("/", response_model=CompanyResponse, status_code=status.HTTP_201_CREATED)
async def create_company(company: CompanyCreate):
    logger.info(f"Creating Company...")
    existing = await prisma.company.find_unique(where={"name": company.name})
    if existing:
        logger.warning(f"Duplicate Company")
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"Company '{company.name}' already exists"
        )
    
    new_company = await prisma.company.create(
        data={
            "name": company.name,
            "description": company.description,
            "email": company.email,
            "address": company.address
        }
    )
    logger.info(f"Company Created : {company.name}")
    return new_company

@router.get("/", response_model=List[CompanyResponse])
async def get_all_companies(skip: int = 0, limit: int = 10):
    logger.info(f"Fetching companies -")
    companies = await prisma.company.find_many(skip=skip, take=limit)
    if not companies:
        logger.warning(f"No Company Found")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail= f"No Company Found"
            )
    return companies

@router.get("/{company_id}", response_model=CompanyResponse)
async def get_company(company_id: int):
    logger.info(f"Fetching company with ID: {company_id}")
    company = await prisma.company.find_unique(where={"id": company_id})
    
    if not company:
        logger.warning(f"Company not found: ID {company_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Company with ID {company_id} not found"
        )
    
    return company

@router.put("/{company_id}", response_model=CompanyResponse)
async def update_company(company_id: int, company_update: CompanyUpdate):    
    logger.info(f"Updating company: ID {id}")
    existing = await prisma.company.find_unique(where={"id": company_id})
    if not existing:
        logger.warning(f"Company not found: ID {id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Company with ID {company_id} not found"
        )
    
    update_data = company_update.model_dump(exclude_unset=True)
    
    updated_company = await prisma.company.update(
        where={"id": company_id},
        data=update_data
    )
    logger.info(f"Company updated successfully: ID {id}")
    return updated_company

@router.delete("/{company_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_company(company_id: int):
    logger.info(f"Deleting company: ID {id}")
    company = await prisma.company.find_unique(where={"id": company_id})
    if not company:
        logger.warning(f"Company not found: ID {id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Company with ID {company_id} not found"
        )
        
    await prisma.company.delete(where={"id": company_id})
    return None
