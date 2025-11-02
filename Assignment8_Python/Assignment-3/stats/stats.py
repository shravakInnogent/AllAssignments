import numpy as np

def show_stats_report(productList):
    print("\nInventory Statistics Report --")

    if not productList:
        print("No products found in inventory.\n")
        return

    prices = np.array([product.price for product in productList])
    stocks = np.array([product.stock for product in productList])

    avg_price = np.mean(prices)
    max_price = np.max(prices)
    total_stock = np.sum(stocks)

    print("\nTotal Inventory Value per Product:")
    for product in productList:
        total_product_value = product.price * product.stock
        print(f"  {product.proName}: ₹{total_product_value:.2f}")

    print(f"\nAverage Price of Items: ₹{avg_price:.2f}")
    print(f"Most Expensive Item Price: ₹{max_price:.2f}")
    print(f"Total Stock Count: {total_stock}")

    tag = input("\nEnter a tag to filter stats (e.g., clearance): ").lower()
    filtered = [p for p in productList if tag in p.tags.lower()]

    if filtered:
        tag_prices = np.array([p.price for p in filtered])
        tag_values = np.array([p.price * p.stock for p in filtered])
        print(f"\nStats for tag '{tag}':")
        print(f"  Average Price: ₹{np.mean(tag_prices):.2f}")
        print(f"  Total Value: ₹{np.sum(tag_values):.2f}\n")
    else:
        print(f"No products found with tag '{tag}'.\n")