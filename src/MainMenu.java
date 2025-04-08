import controllers.CustomerController;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Webshop System!");
            System.out.println("1. Manage Customers");
            System.out.println("2. Manage Products");
            System.out.println("3. Manage Orders");
            System.out.println("4. Exit");

            System.out.print("Select an option: ");
            String select = scanner.nextLine();

            switch (select) {
                case "1" -> {
                    CustomerController customerController = new CustomerController();
                    customerController.runMenu();
                }
                case "2" -> {
                    // ProductController logic goes here
                }
                case "3" -> {
                    // OrderController logic goes here
                }
                case "4" -> {
                    System.out.println("Exiting the system...");
                    exit = true;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }
}