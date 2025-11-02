from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List
from ..database import get_db
from ..models import company as company_model
from ..schemas import company as company_schema
from app.utils.logger import logger


router = APIRouter(
    prefix="/api/companies",
    tags=["Companies"]
)

@router.post("/create", response_model=company_schema.CompanyResponse, status_code=status.HTTP_201_CREATED)
def create_company(company: company_schema.CompanyCreate, db: Session = Depends(get_db)):
    logger.info(f"Creating Company...")
    existing = db.query(company_model.Company).filter(
        company_model.Company.name == company.name
    ).first()
    
    if existing :
        logger.warning(f"Duplicate Company")
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail= f"Company Already Listed"
            ) 
        
    db_company = company_model.Company(**company.model_dump())
    db.add(db_company)
    db.commit()
    db.refresh(db_company)
    logger.info(f"Company Created : {company.name}")
    return db_company
    
@router.get("/getall", response_model=List[company_schema.CompanyResponse], status_code=status.HTTP_200_OK)
def get_companies(db: Session = Depends(get_db)):
    logger.info(f"Fetching companies -")
    companies = db.query(company_model.Company)
    
    if not companies:
        logger.warning(f"No Company Found")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail= f"No Company Found"
            )
    return companies
@router.get("/{company_id}", response_model=company_schema.CompanyResponse)
def get_company_by_id(company_id: int, db: Session = Depends(get_db)):
    logger.info(f"Fetching company with ID: {company_id}")
    company = db.query(company_model.Company).filter(
        company_model.Company.id == company_id
    ).first()
    
    if not company:
        logger.warning(f"Company not found: ID {company_id}")
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail=f"Company with ID {company_id} not found"
        )
    
    return company
@router.delete("/delete/{id}")
def delete_company(id,db: Session= Depends(get_db) ):
   logger.info(f"Deleting company: ID {id}")
   delCompany =  db.query(company_model.Company).filter(
       company_model.Company.id == id
   ).first()
   
   if not delCompany:
       logger.warning(f"Company not found: ID {id}")
       raise HTTPException(
           status_code=status.HTTP_404_NOT_FOUND,
           detail=f"Company Not Found with ID: {id}"
       )
   
   db.delete(delCompany)
   db.commit()
   return f"Company of ID: {id} Deleted Successfully"
   
@router.patch("/update/{id}", response_model=company_schema.CompanyResponse)
def update_company(id,company_update:company_schema.CompanyUpdate, db:Session = Depends(get_db)):
    logger.info(f"Updating company: ID {id}")
    db_company =  db.query(company_model.Company).filter(
        company_model.Company.id == id
    ).first()
    
    if not db_company:
       logger.warning(f"Company not found: ID {id}")
       raise HTTPException(
           status_code=status.HTTP_404_NOT_FOUND,
           detail=f"Company Not Found with ID: {id}"
       )

    update_data = company_update.model_dump(exclude_unset=True)
    for key, value in update_data.items():
        setattr(db_company, key, value)
        
    db.add(db_company)
    db.commit()
    db.refresh(db_company)
    logger.info(f"Company updated successfully: ID {id}")
    return db_company