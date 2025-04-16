package services;

import entities.SessionCart;

import java.util.Map;

public class SessionCartService {
    private final SessionCart sessionCart = new SessionCart();

    public void addProductToCart(int productId, int quantity) {
        sessionCart.addProductToCart(productId, quantity);
    }

    public void removeProductFromCart(int productId) {
        sessionCart.removeProductFromCart(productId);
    }

    public void updateProductQuantity(int productId, int quantity) {
        sessionCart.updateProductQuantity(productId, quantity);
    }

    public Map<Integer, Integer> getProductList() {
        return sessionCart.getItems();
    }

    public void clearCart() {
        sessionCart.clearCart();
    }

    public double calculateTotalPrice() {
        return sessionCart.getTotalPrice();
    }
}
