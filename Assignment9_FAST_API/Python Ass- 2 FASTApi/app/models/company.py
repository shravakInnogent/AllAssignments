from sqlalchemy import Column, Integer, String, Text
from sqlalchemy.orm import relationship
from ..database import Base

class Company(Base):
    __tablename__ = "companies"
    
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, unique=True, nullable=False, index=True)
    description = Column(Text)
    email = Column(String, unique=True, index=True)
    address = Column(String)
    
    # One-to-Many: One company has many products
    products = relationship("Product", back_populates="company", cascade="all, delete-orphan")
