package controllers;

import contexts.LoginContext;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginController {

    public void runLoginMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            System.out.println("Welcome to the Webshop!");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. Exit");

            select = sc.nextLine().trim().toLowerCase();

            switch (select) {
                case "1" -> login(sc);
                case "2" -> logout();
                case "3" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!select.equals("3"));
    }

    private void login(Scanner sc) throws SQLException {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        boolean success = LoginContext.loginAsCustomer(email, password);

        if (success) {
            System.out.println("Login successful!");
            // Redirect to the main menu or customer area
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void logout() {
        LoginContext.logout();
        System.out.println("Logged out successfully.");
    }
}
