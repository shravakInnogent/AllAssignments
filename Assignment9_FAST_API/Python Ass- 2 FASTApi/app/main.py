from fastapi import FastAPI, Request, status
from fastapi.responses import JSONResponse
from fastapi.exceptions import RequestValidationError
from .database import engine, Base
from .routers import companies_routers, products_routers, categories_routers
from app.utils.logger import logger

from app.schemas.company import CompanyResponse
from app.schemas.product import ProductResponse

# Update forward references
CompanyResponse.model_rebuild()
ProductResponse.model_rebuild()


# Create database tables
Base.metadata.create_all(bind=engine)

# Initialize FastAPI app
app = FastAPI(
    title="Company Product Management API",
    description="FastAPI project with SQLAlchemy relationships",
    version="1.0.0"
)
# Include routers
app.include_router(companies_routers.router)
app.include_router(products_routers.router)
app.include_router(categories_routers.router)
