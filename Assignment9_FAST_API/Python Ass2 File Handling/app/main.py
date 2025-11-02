from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.database import lifespan
from app.routers import employees

app = FastAPI(
    title="File Handling API with Prisma",
    description="Upload CSV, Export to CSV, Manual Data Entry",
    version="1.0.0",
    lifespan=lifespan
)

app.include_router(employees.router)