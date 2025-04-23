package services;

import contexts.SessionContext;
import entities.Product;
import entities.carts.SessionCart;
import factories.ServiceFactory;

import java.sql.SQLException;
import java.util.Map;

public class SessionCartService {

    private SessionCart getCart() {
        return SessionContext.getSessionCart();
    }

    public void addProductToCart(int productId,
                                 int quantity) {
        getCart().addProductToCart(productId,
                quantity);
    }

    public Map<Product, Integer> getProductList() {
        return getCart().getItems();
    }

    public void clearCart() {
        getCart().clearCart();
    }

    public double getTotalPrice() {
        return getCart().getTotalPrice();
    }

    public int convertToOrder(int customerId) {
        Map<Product, Integer> items = getCart().getItems();

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        try {
            OrderService orderService = ServiceFactory.getOrderService();
            int orderId = orderService.createOrderFromCart(customerId,
                    items);

            clearCart();
            return orderId;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating order: " + e.getMessage(),
                    e);
        }
    }
}