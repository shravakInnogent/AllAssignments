from prisma import Prisma
from contextlib import asynccontextmanager

prisma = Prisma()

# Lifespan context manager for FastAPI
@asynccontextmanager
async def lifespan(app):
    # Startup: Connect to database
    await prisma.connect()
    print("Connected to database")
    yield
    # Shutdown: Disconnect from database
    await prisma.disconnect()
    print("Disconnected from database")

# Dependency for routes
async def get_db():
    return prisma
