package controllers;

import entities.Customer;
import services.CustomerService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {
    CustomerService customerService = new CustomerService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Get all Customers");
        System.out.println("2. Get one Customer");
        System.out.println("3. Add new Customer");
        System.out.println("4. Update Customer Email");
        System.out.print("Select an option: ");
        String select = sc.nextLine();

        switch (select) {
            case "1" -> getAllCustomers();
            case "2" -> getCustomerById();
            case "3" -> addNewCustomer();
            case "4" -> updateCustomerEmail();
            default -> System.out.println("Invalid option. Please try again.");
        }
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
        int id = sc.nextInt();
        System.out.println(customerService.getCustomer(id));
    }

    private void addNewCustomer() {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter customer email: ");
        String email = sc.nextLine();
        System.out.print("Enter customer phone number: ");
        String phone = sc.nextLine();
        System.out.print("Enter customer address: ");
        String address = sc.nextLine();
        System.out.print("Enter customer password: ");
        String password = sc.nextLine();

        Customer newCustomer = new Customer(customerId, name, email, phone, address, password);
        customerService.addCustomer(newCustomer);

        System.out.println("Customer added successfully.");
    }

    private void updateCustomerEmail() {
        Scanner sc = new Scanner(System.in);
        int customerId = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter customer ID: ");
            if (sc.hasNextInt()) {
                customerId = sc.nextInt();
                sc.nextLine();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid integer for customer ID.");
                sc.nextLine();
            }
        }

        System.out.print("Enter new email for customer: ");
        String newEmail = sc.nextLine();

        customerService.updateCustomerEmail(customerId, newEmail);
        System.out.println("Customer updated successfully.");
    }
}