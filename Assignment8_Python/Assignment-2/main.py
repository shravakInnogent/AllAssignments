from  service import *
def main():
    choice = 0
    while choice != 8:
        try:
            choice = int(input(
                "1.Insert Product\n2.Low Stock Check\n3.List All Products\n4.Delete Product\n5.Update Stock\n6.Total product value\n7.Clearance Stock\n8.Exit\nEnter your choice: "))
            
            # Validate range
            if choice < 1 or choice > 8:
                print("Invalid Choice! Please enter a number between 1 and 8.\n")
                continue
                
        except ValueError:
            print("Invalid input! Please enter a valid integer.\n")
            continue
        
        if choice == 1:
            add_product()
            
        elif choice == 2:
            lowStock()
            
        elif choice == 3:
            show_all()
            
        elif choice == 4:
            try:
                delete_id = int(input("Enter Product ID To Delete That Product: "))
                delete_pro(delete_id)
            except ValueError:
                print("Invalid input! Product ID must be an integer.\n")
                
        elif choice == 5:
            try:
                update_id = int(input("Enter Product ID To Update: "))
                new_stock = int(input("Enter Stock Value: "))
                update_stock(update_id, new_stock)
            except ValueError:
                print("Invalid input! Product ID and Stock must be integers.\n")
                
        elif choice == 6:
            print(f"Total Product Value: {total_value()}\n")
            
        elif choice == 7:
            show_clearance_stock()
            
        elif choice == 8:
            print("Exiting program...")
            break

if __name__ == "__main__":
    main()