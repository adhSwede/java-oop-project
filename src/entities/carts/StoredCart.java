package entities.carts;

public class StoredCart extends Cart {
    private int cartId;
    private int customerId;
    private String name;
    private boolean isActive;

    // #################### [ Constructor ] ####################
    public StoredCart(int cartId, int customerId, String name, boolean isActive) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.name = name;
        this.isActive = isActive;
    }

    // #################### [ Getters & Setters ] ####################
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}