package entities.carts;

import entities.Product;

import java.util.HashMap;
import java.util.Map;

public abstract class Cart {
    protected Map<Product, Integer> items = new HashMap<>();

    // #################### [ Add / Remove / Update ] ####################
    public void addProduct(Product product,
                           int quantity) {
        items.merge(product,
                quantity,
                Integer::sum);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public void updateQuantity(Product product,
                               int quantity) {
        if (quantity <= 0) {
            removeProduct(product);
        } else {
            items.put(product,
                    quantity);
        }
    }

    // #################### [ Totals & State ] ####################
    public double getTotalPrice() {
        return items
                .entrySet()
                .stream()
                .mapToDouble(entry -> entry
                        .getKey()
                        .getPrice() * entry.getValue())
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clearCart() {
        items.clear();
    }

    // #################### [ Getters ] ####################
    public Map<Product, Integer> getItems() {
        return items;
    }
}