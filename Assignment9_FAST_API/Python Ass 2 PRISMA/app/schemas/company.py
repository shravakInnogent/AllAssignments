from pydantic import BaseModel
from typing import Optional, List, TYPE_CHECKING


if TYPE_CHECKING:
    from app.schemas.product import ProductResponse

class CompanyBase(BaseModel):
    name: str
    description: Optional[str] = None
    email: str
    address: Optional[str] = None

class CompanyCreate(CompanyBase):
    pass

class CompanyUpdate(BaseModel):
    name: Optional[str] = None
    description: Optional[str] = None
    email: str = None
    address: Optional[str] = None

class CompanyResponse(CompanyBase):
    id: int
    class Config:
        from_attributes = True

# Company with products
class CompanyWithProducts(CompanyResponse):
    products: List['ProductResponse'] = []
