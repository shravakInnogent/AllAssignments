from product import *
class FoodProduct(Product):
    def __init__(self, id, name, stock, price, location, tags, expiry_date):
        super().__init__(id, name,stock, price, location, tags)
        self.expiry_date = expiry_date

    def __str__(self):
        base_str = super().__str__()
        expiry_str = f" | Expiry: {self.expiry_date}" if self.expiry_date else ""
        # expired_warning = " ⚠EXPIRED" if self.is_expired() else ""
        return base_str + expiry_str