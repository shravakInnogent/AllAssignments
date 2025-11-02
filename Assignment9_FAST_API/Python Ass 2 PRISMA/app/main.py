from fastapi import FastAPI
from app.database import lifespan
from app.routers import categories_routers, companies_routers, products_routers
from app.utils.logger import logger

# Initialize FastAPI with lifespan
app = FastAPI(
    title="Company Product Management API with Prisma",
    description="FastAPI + Prisma ORM",
    version="2.0.0",
    lifespan=lifespan  # Database connection lifecycle
)

# Include routers
app.include_router(companies_routers.router)
app.include_router(products_routers.router)
app.include_router(categories_routers.router)
