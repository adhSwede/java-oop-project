package contexts;

import constants.DefaultCustomers;
import entities.carts.SessionCart;
import entities.users.Admin;
import entities.users.Customer;
import entities.users.User;

public class SessionContext {
    private static SessionCart sessionCart = new SessionCart();
    private static User currentUser = DefaultCustomers.GUEST;

    // Set user (either Customer or Admin)
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Get user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Convenience
    public static boolean isGuest() {
        return currentUser.getUserId() == DefaultCustomers.GUEST.getUserId();
    }

    public static boolean isAdmin() {
        return currentUser instanceof Admin;
    }

    public static boolean isCustomer() {
        return currentUser instanceof Customer && !isGuest();
    }

    public static Customer getCurrentCustomer() {
        return (currentUser instanceof Customer customer) ? customer : null;
    }

    public static void clear() {
        currentUser = DefaultCustomers.GUEST;
        sessionCart = new SessionCart();
    }

    public static SessionCart getSessionCart() {
        return sessionCart;
    }
}