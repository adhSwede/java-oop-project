package services;

import entities.Product;
import entities.carts.SessionCart;
import contexts.SessionContext;

import java.util.Map;

public class SessionCartService {

    private SessionCart getCart() {
        return SessionContext.getSessionCart();
    }

    public void addProductToCart(int productId, int quantity) {
        getCart().addProductToCart(productId, quantity);
    }

    public void removeProductFromCart(int productId) {
        getCart().removeProductFromCart(productId);
    }

    public void updateProductQuantity(int productId, int quantity) {
        getCart().updateProductQuantity(productId, quantity);
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
}
