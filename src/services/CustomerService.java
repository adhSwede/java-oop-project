package services;

import entities.users.Customer;
import entities.users.User;
import factories.RepositoryFactory;
import repositories.CustomerRepository;
import utils.ValidationUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository = RepositoryFactory.getCustomerRepository();

    // #################### [ Get ] ####################
    public ArrayList<Customer> getAllCustomers() {
        try {
            return customerRepository.getAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public Customer getCustomer(int id) {
        try {
            return customerRepository.getById(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean customerExists(int customerId) throws SQLException {
        return customerRepository.existsById(customerId);
    }

    // #################### [ Insert ] ####################
    public boolean addCustomer(Customer customer) {
        if (!ValidationUtils.isValidEmail(customer.getEmail())) {
            System.out.println("Invalid email format. Please try again.");
            return false;
        }

        List<String> issues = ValidationUtils.getPasswordErrors(customer.getPassword());
        if (!issues.isEmpty()) {
            issues.forEach(System.out::println);
            return false;
        }

        try {
            customerRepository.addCustomer(customer);
            return true;
        } catch (SQLException e) {
            System.out.println("Error while adding customer: " + e.getMessage());
            return false;
        }
    }

    // #################### [ Update ] ####################
    public void updateCustomerEmail(int customerId,
                                    String email) {
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }
        try {
            customerRepository.updateCustomerEmail(customerId,
                    email);
        } catch (SQLException e) {
            System.out.println("Error while updating customer email: " + e.getMessage());
        }
    }

    // #################### [ Auth ] ####################
    public User loginAsCustomer(String email,
                                String password) throws SQLException {
        Customer customer = customerRepository.getByEmail(email);

        if (customer != null && customer
                .getPassword()
                .equals(password)) {
            return customer;
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}
