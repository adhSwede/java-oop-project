package services;

import utils.ValidationUtils;
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
            return new ArrayList<>();
        }
    }

    // Get customer by ID
    public Customer getCustomer(int id) {
        try {
            return customerRepository.getById(id);
        } catch (SQLException e) {
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
            System.out.println("Error while adding customer: " + e.getMessage());
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
            System.out.println("Error while updating customer email: " + e.getMessage());
        }
    }
}