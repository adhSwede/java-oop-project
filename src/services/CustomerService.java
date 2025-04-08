package services;

import Utils.ExceptionHandler;
import Utils.ValidationUtils;
import entities.Customer;
import repositories.CustomerRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerService {
    CustomerRepository customerRepository = new CustomerRepository();

    // Get all customers
    public ArrayList<Customer> getAllCustomers() {
        try {
            return customerRepository.getAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "fetching all customers");
            return new ArrayList<>(); // Return empty list on error
        }
    }

    // Get customer by ID
    public Customer getCustomer(int id) {
        try {
            return customerRepository.getById(id);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "fetching customer by ID");
            return null;
        }
    }

    // Add a new customer
    public void addCustomer(Customer customer) {
        if (!ValidationUtils.isValidEmail(customer.getEmail())) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }

        try {
            customerRepository.addCustomer(customer);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "adding customer");
        }
    }

    // Update customer email
    public void updateCustomerEmail(int customerId, String email) {
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }

        try {
            customerRepository.updateCustomerEmail(customerId, email);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "updating customer email");
        }
    }
}