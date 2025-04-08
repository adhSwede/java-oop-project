package entities;

public class Product {
    private int productId;
    private String name;


    public Product(int productId, String name) {
        this.productId = productId;
        this.name = name;
    }


    //    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + name;
    }
}