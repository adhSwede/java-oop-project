package repositories;
import entities.Customer;
import utils.SqlUtils;

import java.sql.*;
import java.util.ArrayList;

import static utils.Mappers.customerMapper;

public class CustomerRepository {

    // Fetch all customers
    public ArrayList<Customer> getAll() throws SQLException {
        String query = "SELECT * FROM customers";

        return SqlUtils.executeAndMap(query, customerMapper);
    }

    // Fetch customer by ID
    public Customer getById(int id) throws SQLException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";

        ArrayList<Customer> customers = SqlUtils.executeAndMap(
                query,
                customerMapper,
                String.valueOf(id)
        );

        return customers.isEmpty() ? null : customers.getFirst();
    }


    // Add new customer
    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
        SqlUtils.executeUpdate(query,
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getPassword()
        );
    }

    // Update customer email
    public void updateCustomerEmail(int customerId, String email) throws SQLException {
        String query = "UPDATE customers SET email = ? WHERE customer_id = ?";
        SqlUtils.executeUpdate(query,
                email,
                String.valueOf(customerId)
        );
    }

    @SuppressWarnings("SqlResolve")
    public boolean existsById(int customerId) throws SQLException {
        String query = "SELECT 1 FROM customers WHERE customer_id = ?";
        try (Connection conn = SqlUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}