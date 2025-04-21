package entities;

import factories.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productId;
    private int manufacturerId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private List<Category> categories = new ArrayList<>();

    // #################### [ Constructors ] ####################
    public Product(int productId, int manufacturerId, String name, String description, double price, int stockQuantity) {
        this.productId = productId;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Product(int manufacturerId, String name, String description, double price, int stockQuantity) {
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // #################### [ Getters & Setters ] ####################
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    // #################### [ ToString ] ####################
    @Override
    public String toString() {
        String manufacturerName;
        try {
            manufacturerName = ServiceFactory.getManufacturerService().getManufacturerNameById(manufacturerId);
        } catch (Exception e) {
            manufacturerName = "Unknown";
        }

        return "Product ID: " + productId +
                ", Name: " + name +
                ", Manufacturer: " + manufacturerName +
                ", Price: $" + price +
                ", In stock: " + stockQuantity;
    }
}