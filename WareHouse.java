
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Product implements Serializable {
    private String productID;
    private String name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{ID=" + productID + ", Name=" + name + ", Quantity=" + quantity + ", Location=" + location + '}';
    }
}

class Location implements Serializable {
    private int aisle, shelf, bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "Aisle " + aisle + ", Shelf " + shelf + ", Bin " + bin;
    }
}

class Order implements Comparable<Order> {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;

    public enum Priority {
        STANDARD, EXPEDITED
    }

    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }

    public String getOrderID() {
        return orderID;
    }

    public List<String> getProductIDs() {
        return productIDs;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Order other) {
        return this.priority.ordinal() - other.priority.ordinal();
    }

    @Override
    public String toString() {
        return "Order{ID=" + orderID + ", Priority=" + priority + ", Products=" + productIDs + "}";
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

class InvalidLocationException extends Exception {
    public InvalidLocationException(String message) {
        super(message);
    }
}

class InventoryManager {
    private ConcurrentHashMap<String, Product> products;
    private PriorityQueue<Order> orderQueue;
    private ExecutorService executor;
    private static final String INVENTORY_FILE = "inventory_data.txt";

    public InventoryManager() {
        products = new ConcurrentHashMap<>();
        orderQueue = new PriorityQueue<>();
        executor = Executors.newFixedThreadPool(3);
        loadInventory();
    }

    public synchronized void addProduct(Product product) {
        products.put(product.getProductID(), product);
        System.out.println("Added product: " + product);
    }

    public synchronized void processOrders() {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            executor.submit(() -> fulfillOrder(order));
        }
    }

    private void fulfillOrder(Order order) {
        System.out.println("Processing Order: " + order);
        for (String productID : order.getProductIDs()) {
            try {
                updateStock(productID);
            } catch (OutOfStockException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void updateStock(String productID) throws OutOfStockException {
        Product product = products.get(productID);
        if (product == null || product.getQuantity() == 0) {
            throw new OutOfStockException("Product ID " + productID + " is out of stock.");
        }
        synchronized (product) {
            product.setQuantity(product.getQuantity() - 1);
        }
        System.out.println("Updated stock: " + product);
    }

    public void addOrder(Order order) {
        orderQueue.add(order);
        System.out.println("Order added: " + order);
    }

    public void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(products);
            System.out.println("Inventory saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_FILE))) {
            products = (ConcurrentHashMap<String, Product>) ois.readObject();
            System.out.println("Inventory loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous inventory found.");
        }
    }
}

public class Warehouse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        while (true) {
            System.out.println("\n1. Add Product\n2. Place Order\n3. Process Orders\n4. Save Inventory\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    String productID = scanner.nextLine();
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter Aisle: ");
                    int aisle = scanner.nextInt();
                    System.out.print("Enter Shelf: ");
                    int shelf = scanner.nextInt();
                    System.out.print("Enter Bin: ");
                    int bin = scanner.nextInt();
                    scanner.nextLine();
                    inventoryManager
                            .addProduct(new Product(productID, name, quantity, new Location(aisle, shelf, bin)));
                    break;

                case 2:
                    System.out.print("Enter Order ID: ");
                    String orderID = scanner.nextLine();
                    System.out.print("Enter Number of Products: ");
                    int numProducts = scanner.nextInt();
                    scanner.nextLine();
                    List<String> productIDs = new ArrayList<>();
                    for (int i = 0; i < numProducts; i++) {
                        System.out.print("Enter Product ID: ");
                        productIDs.add(scanner.nextLine());
                    }
                    System.out.print("Enter Priority (1 for STANDARD, 2 for EXPEDITED): ");
                    int priorityChoice = scanner.nextInt();
                    Order.Priority priority = (priorityChoice == 2) ? Order.Priority.EXPEDITED
                            : Order.Priority.STANDARD;
                    inventoryManager.addOrder(new Order(orderID, productIDs, priority));
                    break;

                case 3:
                    inventoryManager.processOrders();
                    break;

                case 4:
                    inventoryManager.saveInventory();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    inventoryManager.saveInventory();
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}