package contexts;

import entities.users.Customer;
import entities.users.User;
import services.CustomerService;

import java.sql.SQLException;

public class LoginContext {
    private static Customer loggedInCustomer;  // Singleton for current session

    // Log in the user
    public static boolean loginAsCustomer(String email,
                                          String password) throws SQLException {
        CustomerService customerService = new CustomerService();
        User user = customerService.loginAsCustomer(email,
                password);
        if (user instanceof Customer customer) {
            loggedInCustomer = customer;
            return true;
        }
        return false;
    }

    // Log out the user
    public static void logout() {
        loggedInCustomer = null;
        SessionContext.clear();
    }

    // Check if a user is logged in
    public static boolean isLoggedIn() {
        return loggedInCustomer != null;
    }

    // Get the logged-in user
    public static Customer getLoggedInUser() {
        return loggedInCustomer;
    }
}