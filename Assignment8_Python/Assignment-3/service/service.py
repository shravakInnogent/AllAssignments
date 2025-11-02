

from models.food_product import FoodProduct
from models.product import Product

class Inventory:
    def __init__(self):
        id =1
        self.productList = [
            FoodProduct(id,"Sanchi Milk", 20, 50.0, "Shelf-1", "clearance", "2025-10-30"),
            FoodProduct(id+1,"Mango", 15, 30.0, "Shelf-2", "clearance", "2025-10-30"),
            Product(id+2,"Bottle", 50, 12.0, "Shelf-3", "Available"),
            Product(id+3,"Dettol Soap", 25, 40.0, "Shelf-6","clearance")
        ]
        

    def add_product(self):
        print("Choose Product Type\n")
        print("1.Regular Product\n2.Food Product With Expiry\n")
        choice = int(input("Enter Choice (1/2): "))
        print("Enter Product Details: Product Name, Stock, Price, Location, Tag")
        pro_id = 4
        pro_name = input()
        pro_stock = int(input())
        pro_price = int(input())
        pro_location = input()
        pro_tags = input().lower()
        if choice == 2:
            expiry_date = input("Enter expiry date (YYYY-MM-DD): ")
            product = FoodProduct(pro_id+1, pro_name, pro_stock, pro_price, pro_location, pro_tags, expiry_date)
        else:
            product = Product(pro_id+1, pro_name, pro_stock, pro_price, pro_location, pro_tags)
        self.productList.append(product)
        
    def lowStock(self):
        for product in self.productList:
            if product.stock < 5:
               print(f"Warning ⚠️ Only {product.stock} quantity available of product, ID:", product.id)
               return
        print("All stock are available in large quantity.")
    def show_details(self,pro_id):
        for product in self.productList:
            if product.id == pro_id :
                print(f"Product Id ", product.id)
                print(f"Title : ", product.proName)
                print(f"Stock : ", product.stock)
                if product.tags.lower() == "clearance":
                    print(f"Price : ", product.price / 2, "(50 % Discount)")
                else:
                    print(f"Price : ", product.price)
                print(f"Location : ", product.location)
                print(f"Tags : ", product.tags, "\n")
                return

    def show_all(self):
        if not self.productList:
            print("No Products Found\n")
            return
        print("All Products:")
        for product in self.productList:
            print(product)



    def delete_pro(self,delete_id):
        for product in self.productList:
            if product.id == delete_id:
                self.productList.remove(product)
                print("Product ",product.id, "deleted")
                return
        print("No product found with product id ", delete_id)


    def update_stock(self,update_id, new_stock):
        for product in self.productList:
            if product.id == update_id:
                product.stock = new_stock
                print("Product ",product.id, "updated")
                return
        print("No product found with product id ",update_id)

    def total_value(self):
        total = 0
        for product in self.productList:
            if product.tags.lower() == "clearance":
                total = total + product.price/2 * product.stock
            else:
                total += product.price * product.stock
        return total

    def show_clearance_stock(self):
        flag = 0
        print("Clearance Stock:")
        for product in self.productList:
            if product.tags.lower() == "clearance":
                flag = 1
                self.show_details(product.id)
        if not flag  :
            print("No Product In Clearance Stock")
        return
    
