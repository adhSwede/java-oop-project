package controllers;

import constants.DefaultCustomers;
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
            ConsoleHelper.printHeader("Webshop Start Menu");

            ConsoleHelper.printOption("1", "Browse as Customer");
            ConsoleHelper.printOption("2", "Login as Customer");
            ConsoleHelper.printOption("3", "Login as Admin");
            ConsoleHelper.printOption("X", "Exit");

            ConsoleHelper.prompt("Choose an option: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1" -> new ProductController().runMenu(); // Customer browsing view
                case "2" -> handleCustomerLogin(scanner);
                case "3" -> handleAdminLogin(scanner);
                case "4" -> {
                    SessionContext.setCurrentCustomer(DefaultCustomers.GUEST);
                    SessionContext.resetCart();
                    ConsoleHelper.printSuccess("You have been logged out.");
                }

                case "x" -> {
                    ConsoleHelper.printSuccess("Thanks for visiting!");
                    exit = true;
                }
                default -> ConsoleHelper.printWarning("Invalid selection. Please try again.");
            }
        }
    }

    private void handleCustomerLogin(Scanner scanner) throws SQLException {
        ConsoleHelper.printHeader("Customer Login");

        ConsoleHelper.prompt("Email: ");
        String email = scanner.nextLine();

        ConsoleHelper.prompt("Password: ");
        String password = scanner.nextLine();

        if (LoginContext.loginAsCustomer(email, password)) {
            Customer customer = LoginContext.getLoggedInUser();
            SessionContext.setCurrentCustomer(customer); // if still keeping SessionContext
            ConsoleHelper.printSuccess("Logged in as " + customer.getName());
            new CustomerController().runMenu();
        } else {
            ConsoleHelper.printError("Invalid email or password.");
        }
    }


    private void handleAdminLogin(Scanner scanner) {
        ConsoleHelper.printHeader("Admin Login");

        ConsoleHelper.prompt("Username: ");
        String username = scanner.nextLine();

        ConsoleHelper.prompt("Password: ");
        String password = scanner.nextLine();

        AdminService adminService = new AdminService();

        try {
            boolean isValid = adminService.login(username, password);

            if (isValid) {
                ConsoleHelper.printSuccess("Welcome, Admin!");
                new AdminController().runPreMenu();
            } else {
                ConsoleHelper.printError("Invalid admin credentials.");
            }

        } catch (SQLException e) {
            ConsoleHelper.printError("Login failed due to a system error.");
        }
    }
}