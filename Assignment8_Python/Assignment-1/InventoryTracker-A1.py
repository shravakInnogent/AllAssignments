# from prompt_toolkit.contrib.regular_languages.compiler import Match

def addProduct(id, proName, proStock, proPrice, proLocation, proTags):
    product = Product(id, proName, proStock, proPrice, proLocation, proTags)
    productList.append(product)
    print("----------------------\nProduct Added!\n----------------------")

def showDetails(proId):
    for product in productList:
        if product.id == proId :
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
def lowStock():
    for product in productList:
        if product.stock < 5:
            print(f"Warning ⚠️ Only {product.stock} quantity available of product, ID:", product.id)
            return
    print("All stock are available in large quantity.")
def showAll():
    if not productList:
        print("No Products Found\n")
        return
    print("All Products:")
    for product in productList:
        print(f"Product Id ",product.id)
        print(f"Title : ",product.proName)
        print(f"Stock : ",product.stock)
        if product.tags == "Clearance":
            print(f"Price : ",product.price/2, "(50 % Discount)")
        else:
            print(f"Price : ",product.price)
        print(f"Location : ",product.location)
        print(f"Tags : ",product.tags,"\n")

def deletePro(deleteId):
    for product in productList:
        if product.id == deleteId:
            productList.remove(product)
            print("Product ",product.id, "deleted")
            return
    print("No product found with product id ", updateId)


def updateStock(updateId, newStock):
    for product in productList:
        if product.id == updateId:
            product.stock = newStock
            print("Product ",product.id, "updated")
            return
    print("No product found with product id ",updateId)

def totalValue():
    total = 0
    for product in productList:
        if product.tags.lower() == "clearance":
            total = total + product.price/2 * product.stock
        else:
            total += product.price * product.stock
    return total

def showClearanceStock():
    flag = 0
    print("Clearance Stock:")
    for product in productList:
        if product.tags.lower() == "clearance":
            flag = 1
            showDetails(product.id)
    if not flag  :
        print("No Product In Clearance Stock")
    return


class Product:
    def __init__(self,id, proName, stock, price,location,tags):
      self.id = id
      self.proName = proName
      self.stock = stock
      self.price = price
      self.location = location
      self.tags  = tags

p1 = Product(1,"iPhone", 5, 27000, "Shelf1", "Clearance")
productList= [p1]
print("Enter your choice:")

choice = 0
while choice != 8:
    print("1.Insert Product\n2.Low Stock Check\n3.List All Products\n4.Delete Product\n5.Update Stock\n6.Total product value\n7.Clearance Stock\n8.Exit\n")
    try:
        choice = int(input("Enter your choice (1-8): "))
        # Validate range
        if choice < 1 or choice > 8:
            print("Invalid choice! Please enter a number between 1 and 8.")
            continue
            
    except ValueError:
        print("Invalid input! Please enter a valid integer.")
        continue
    if choice == 1:
        print("Enter Product Details: Product Name, Stock, Price, Location, Tag")
        proId = 1
        proName = input("Product Name:")
        try:
            proStock = int(input("Stock: "))
            proPrice = int(input("Price: "))
        except ValueError:
            print("Stock and Price must be integers!")
            continue   
        proLocation = input()
        proTags = input("Tags: ")
        addProduct(proId+1, proName, proStock, proPrice, proLocation, proTags)

    elif choice == 2:
        lowStock()
    elif choice == 3:
        showAll()
    elif choice == 4:
        try:
            deleteId = int(input("Enter Product ID To Delete That Product: "))
            deletePro(deleteId)
        except ValueError:
            print("Product ID must be an integer!")
    elif choice == 5:
        try:
            updateId = int(input("Enter Product ID To Update : "))
            newStock = int(input("Enter Stock Value : "))
        except ValueError:
            print("Product ID and Stock must be integers!")       
        updateStock(updateId,newStock)
    elif choice == 6:
        print(f"Toatal Product Value: {totalValue()}\n" )
    elif choice == 7:
        showClearanceStock()
    elif choice == 8:
        exit()
