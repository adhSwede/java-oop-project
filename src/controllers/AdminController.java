package controllers;

import utils.ConsoleHelper;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminController {
    public void runPreMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            ConsoleHelper.printHeader("Admin Access");

            ConsoleHelper.printOption("1", "Go to Admin Menu");
            ConsoleHelper.printOption("2", "Switch to Customer View");
            ConsoleHelper.printOption("B", "Back to Main Menu");
            ConsoleHelper.prompt("Select an option: ");

            String choice = sc.nextLine().trim().toLowerCase();
            switch (choice) {
                case "1" -> runMenu();
                case "2" -> new CustomerController().runMenu();
                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid input.");
            }
        }
    }

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            ConsoleHelper.printHeader("Admin Menu");
            ConsoleHelper.printOption("1", "Manage Customers");
            ConsoleHelper.printOption("2", "Manage Products");
            ConsoleHelper.printOption("3", "Manage Orders");
            ConsoleHelper.printOption("B", "Back to previous menu");
            ConsoleHelper.prompt("Select an option: ");

            String choice = sc.nextLine().trim().toLowerCase();
            switch (choice) {
                case "1" -> new CustomerController().runMenu(); // optional: AdminCustomerController?
                case "2" -> new ProductController().runMenu();
                case "3" -> new OrderController().runMenu();
                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid input.");
            }
        }
    }
}