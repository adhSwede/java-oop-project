package controllers;

import entities.Customer;
import services.CustomerService;
import utils.ConsoleHelper;
import utils.InputUtils;
import utils.ValidationUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {
    CustomerService customerService = new CustomerService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.clearConsole();
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Get all customers");
            System.out.println("2. Get one customer by ID");
            System.out.println("3. Add new customer");
            System.out.println("4. Update customer email");
            System.out.println("B. Back to main menu");
            System.out.print("Select an option: ");
            select = sc.nextLine().trim().toLowerCase();

            switch (select) {
                case "1" -> getAllCustomers();
                case "2" -> getCustomerById();
                case "3" -> addNewCustomer();
                case "4" -> updateCustomerEmail();
                case "b" -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!select.equals("b"));
    }

    private void getAllCustomers() {
        ArrayList<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void getCustomerById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter customer ID: ");
        String input = sc.nextLine();

        if (ValidationUtils.isValidInteger(input)) {
            int id = Integer.parseInt(input);
            System.out.println(customerService.getCustomer(id));
        } else {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void addNewCustomer() {
        Scanner sc = new Scanner(System.in);

        String name = InputUtils.promptUntilValid(
                sc,
                "Enter customer name: ",
                input -> !input.isBlank(),
                "Name cannot be empty."
        );

        String email = InputUtils.promptUntilValid(
                sc,
                "Enter customer email: ",
                ValidationUtils::isValidEmail,
                "Invalid email format."
        );

        String phone = InputUtils.promptUntilValid(
                sc,
                "Enter customer phone number: ",
                ValidationUtils::isValidPhoneNumber,
                "Invalid phone number. Must be 10 digits."
        );

        String address = InputUtils.promptUntilValid(
                sc,
                "Enter customer address: ",
                input -> !input.isBlank(),
                "Address cannot be empty."
        );

        String password = InputUtils.promptUntilValid(
                sc,
                "Enter customer password: ",
                input -> !input.isBlank(),
                "Password cannot be empty."
        );

        Customer newCustomer = new Customer(name, email, phone, address, password);
        customerService.addCustomer(newCustomer);
        System.out.println("Customer added successfully.");
    }

    private void updateCustomerEmail() {
        Scanner sc = new Scanner(System.in);

        String idInput = InputUtils.promptUntilValid(
                sc,
                "Enter customer ID: ",
                ValidationUtils::isValidInteger,
                "Invalid input. Please enter a valid integer for customer ID."
        );
        int customerId = Integer.parseInt(idInput);

        String newEmail = InputUtils.promptUntilValid(
                sc,
                "Enter new email for customer: ",
                ValidationUtils::isValidEmail,
                "Invalid email format."
        );

        customerService.updateCustomerEmail(customerId, newEmail);
        System.out.println("Customer updated successfully.");
    }
}