package contexts;

import constants.DefaultCustomers;
import entities.carts.SessionCart;
import entities.users.Admin;
import entities.users.Customer;
import entities.users.User;
import utils.ConsoleHelper;

public class SessionContext {
    private static SessionCart sessionCart = new SessionCart();
    private static User currentUser = DefaultCustomers.GUEST;

    // Get user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Set user (either Customer or Admin)
    public static void setCurrentUser(User user) {
        currentUser = user;
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

    // Safe way to get the logged-in user ID
    public static Integer getLoggedInUserId() {
        if (getCurrentCustomer() == null) {
            ConsoleHelper.printError("You must be logged in to perform this action.");
            return null;
        }
        return getCurrentCustomer().getUserId();
    }

    public static void clear() {
        currentUser = DefaultCustomers.GUEST;
        sessionCart = new SessionCart();
    }

    public static SessionCart getSessionCart() {
        return sessionCart;
    }
}