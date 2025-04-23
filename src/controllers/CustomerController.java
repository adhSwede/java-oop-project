package controllers;

import contexts.SessionContext;
import entities.users.Customer;
import factories.ServiceFactory;
import services.CustomerService;
import utils.ConsoleHelper;
import utils.InputUtils;
import utils.ValidationUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {
    private final CustomerService customerService = ServiceFactory.getCustomerService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.printHeader("👤 Customer Menu");
            ConsoleHelper.printOption("1",
                    "Get all customers");
            ConsoleHelper.printOption("2",
                    "Get one customer by ID");
            ConsoleHelper.printOption("3",
                    "Add new customer");
            ConsoleHelper.printOption("4",
                    "Update customer email");
            ConsoleHelper.printOption("B",
                    "Back to previous menu");
            ConsoleHelper.prompt("Select an option: ");

            select = sc
                    .nextLine()
                    .trim()
                    .toLowerCase();

            switch (select) {
                case "1" -> getAllCustomers();
                case "2" -> getCustomerById();
                case "3" -> addNewCustomer();
                case "4" -> updateCustomerEmail();
                case "b" -> ConsoleHelper.printSuccess("Returning to main menu...");
                default -> ConsoleHelper.printWarning("Invalid option. Please try again.");
            }

        } while (!select.equals("b"));
    }

    private void getAllCustomers() {
        ArrayList<Customer> customers = customerService.getAllCustomers();
        ConsoleHelper.printDivider();
        if (customers.isEmpty()) {
            ConsoleHelper.printWarning("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private void getCustomerById() {
        Scanner sc = new Scanner(System.in);
        ConsoleHelper.prompt("Enter customer ID: ");
        String input = sc.nextLine();

        ConsoleHelper.printDivider();
        if (ValidationUtils.isValidInteger(input)) {
            int id = Integer.parseInt(input);
            Customer customer = customerService.getCustomer(id);
            if (customer != null) {
                System.out.println(customer);
            } else {
                ConsoleHelper.printWarning("Customer not found.");
            }
        } else {
            ConsoleHelper.printWarning("Invalid input. Please enter a valid number.");
        }
    }

    private void addNewCustomer() {
        Scanner sc = new Scanner(System.in);

        ConsoleHelper.printDivider();
        String name = InputUtils.promptUntilValid(sc,
                "Enter customer name: ",
                input -> !input.isBlank(),
                "Name " +
                        "cannot be empty.");

        String email = InputUtils.promptUntilValid(sc,
                "Enter customer email: ",
                ValidationUtils::isValidEmail,
                "Invalid email format.");

        String phone = InputUtils.promptUntilValid(sc,
                "Enter customer phone number: ",
                ValidationUtils::isValidPhoneNumber,
                "Invalid phone number. Must be 10 digits.");

        String address = InputUtils.promptUntilValid(sc,
                "Enter customer address: ",
                input -> !input.isBlank(),
                "Address cannot be empty.");

        String password = InputUtils.promptUntilValid(sc,
                "Enter customer password: ",
                input -> !input.isBlank(),
                "Password cannot be empty.");

        Customer newCustomer = new Customer(name,
                email,
                phone,
                address,
                password);
        customerService.addCustomer(newCustomer);
        ConsoleHelper.printSuccess("Customer added successfully.");
    }

    private void updateCustomerEmail() {
        Scanner sc = new Scanner(System.in);

        ConsoleHelper.printDivider();
        if (SessionContext.isGuest()) {
            ConsoleHelper.printWarning("You must be logged in to update your email.");
            return;
        }

        int customerId = SessionContext
                .getCurrentCustomer()
                .getUserId();

        String newEmail = InputUtils.promptUntilValid(sc,
                "Enter new email for customer: ",
                ValidationUtils::isValidEmail,
                "Invalid email format.");

        customerService.updateCustomerEmail(customerId,
                newEmail);
        ConsoleHelper.printSuccess("Customer updated successfully.");
    }
}