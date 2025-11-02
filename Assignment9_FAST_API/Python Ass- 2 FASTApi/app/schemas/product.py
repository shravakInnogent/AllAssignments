from pydantic import BaseModel, Field
from typing import Optional, TYPE_CHECKING

from app.schemas.category import CategoryResponse


if TYPE_CHECKING:
    from app.schemas.company import CompanyResponse

class ProductBase(BaseModel):
    name: str
    description: Optional[str] = None
    price: float = Field(gt=0, description="Price must be greater than 0")
    stock: int = Field(ge=0, description="Stock cannot be negative")
    company_id: int
    category_id: int

class ProductCreate(ProductBase):
    pass

class ProductUpdate(BaseModel):
    name: Optional[str] = None
    description: Optional[str] = None
    price: Optional[float] = Field(None, gt=0)
    stock: Optional[int] = Field(None, ge=0)
    company_id: Optional[int] = None
    category_id: Optional[int] = None

class ProductResponse(BaseModel):
    id: int
    name: str
    description: Optional[str]
    price: float
    stock: int
    company_id: int
    category_id: int
    
    class Config:
        from_attributes = True

# Product with relationships
class ProductDetailResponse(ProductResponse):
    company: 'CompanyResponse'
    category: 'CategoryResponse'
