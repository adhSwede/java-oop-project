package contexts;

import constants.DefaultCustomers;
import entities.users.Customer;
import entities.carts.SessionCart;

public class SessionContext {
    private static SessionCart sessionCart = new SessionCart();
    private static Customer currentCustomer = DefaultCustomers.GUEST;

    // Session customer data
    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    public static boolean isGuest() {
        return currentCustomer.getUserId() == DefaultCustomers.GUEST.getUserId();
    }

    // Session Cart data
    public static SessionCart getSessionCart() {
        return sessionCart;
    }

    public static void resetCart() {
        sessionCart = new SessionCart();
    }
}