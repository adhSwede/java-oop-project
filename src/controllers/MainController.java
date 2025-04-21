package controllers;

import entities.users.Admin;
import entities.users.Customer;
import factories.ServiceFactory;
import services.AdminService;
import services.CustomerService;
import contexts.*;
import utils.ConsoleHelper;

import java.sql.SQLException;
import java.util.Scanner;

public class MainController {
    final private CustomerService customerService = ServiceFactory.getCustomerService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            ConsoleHelper.printHeader("🏠 Webshop Start Menu");

            ConsoleHelper.printOption("1", "Browse as Customer");
            ConsoleHelper.printOption("2", "Login as Customer");
            ConsoleHelper.printOption("3", "Login as Admin");
            ConsoleHelper.printOption("X", "Exit");

            ConsoleHelper.prompt("Choose an option: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1" -> new CustomerShopController().runCustomerShopMenu();
                case "2" -> handleCustomerLogin(scanner);
                case "3" -> handleAdminLogin(scanner);
                case "x" -> {
                    ConsoleHelper.printSuccess("Thanks for visiting!");
                    exit = true;
                }
                default -> ConsoleHelper.printWarning("Invalid selection. Please try again.");
            }
        }
    }

    private void handleCustomerLogin(Scanner scanner) {
        ConsoleHelper.printHeader("👥 Customer Login");

        ConsoleHelper.prompt("Email: ");
        String email = scanner.nextLine();

        ConsoleHelper.prompt("Password: ");
        String password = scanner.nextLine();

        try {
            if (LoginContext.loginAsCustomer(email, password)) {
                Customer customer = LoginContext.getLoggedInUser();
                SessionContext.setCurrentUser(customer); // <-- updated to match new context logic
                ConsoleHelper.printSuccess("Logged in as " + customer.getName());
                new CustomerShopController().runCustomerShopMenu();
            } else {
                ConsoleHelper.printError("Invalid email or password.");
            }
        } catch (IllegalArgumentException e) {
            ConsoleHelper.printError(e.getMessage());
        } catch (SQLException e) {
            ConsoleHelper.printError("Login failed due to a system error.");
        }

        ConsoleHelper.printDivider();
    }

    private void handleAdminLogin(Scanner scanner) {
        ConsoleHelper.printHeader("🛡️ Admin Login");

        ConsoleHelper.prompt("Username: ");
        String username = scanner.nextLine();

        ConsoleHelper.prompt("Password: ");
        String password = scanner.nextLine();

        AdminService adminService = new AdminService();

        try {
            Admin admin = adminService.getAdminIfValid(username, password);

            if (admin != null) {
                SessionContext.setCurrentUser(admin); // ✅ Store in session
                ConsoleHelper.printSuccess("Welcome, Admin!");
                new AdminController().runPreMenu();
            } else {
                ConsoleHelper.printError("Invalid admin credentials.");
            }

        } catch (SQLException e) {
            ConsoleHelper.printError("Login failed due to a system error.");
        }

        ConsoleHelper.printDivider();
    }
}