from prisma import Prisma
from contextlib import asynccontextmanager

prisma = Prisma()

@asynccontextmanager
async def lifespan(app):
    await prisma.connect()
    print("Connected to database")
    yield
    await prisma.disconnect()
    print("Disconnected from database")

async def get_db():
    return prisma
