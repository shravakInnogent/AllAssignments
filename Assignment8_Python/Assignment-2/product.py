class Product:
    def __init__(self, id, pro_name, stock, price,location,tags):
      self.id = id
      self.proName = pro_name
      self.stock = stock
      self.price = price
      self.location = location
      self.tags  = tags

    def __str__(self):
        return f"{self.proName} | Stock: {self.stock} | Price: ${self.price:.2f} | Location: {self.location} | Tag: {self.tags}"
