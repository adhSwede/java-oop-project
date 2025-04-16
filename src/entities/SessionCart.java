package entities;

import services.ProductService;
import utils.ExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SessionCart {
    ProductService productService = new ProductService();
    private final Map<Integer, Integer> productList = new HashMap<>();

    public void addProductToCart(int productId, int quantity) {
        productList.merge(productId, quantity, Integer::sum); // Adds or updates
    }

    public void removeProductFromCart(int productId) {
        productList.remove(productId);
    }

    public void updateProductQuantity(int productId, int quantity) {
        if (quantity <= 0) {
            removeProductFromCart(productId);
        } else {
            productList.put(productId, quantity);
        }
    }

    public void clearCart() {
        productList.clear();
    }

    public Map<Integer, Integer> getItems() {
        return productList;
    }

    public boolean isEmpty() {
        return productList.isEmpty();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : productList.entrySet()) {
            try {
                Product p = productService.getProductById(entry.getKey());
                total += p.getPrice() * entry.getValue();
            } catch (SQLException e) {
                ExceptionHandler.handleSQLException(e, "retrieving product for cart total");
            }
        }
        return total;
    }
}