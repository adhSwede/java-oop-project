package repositories;

import entities.Customer;
import utils.RowMapper;
import utils.SqlUtils;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {
    private static final RowMapper<Customer> customerMapper = resultSet -> new Customer(
            resultSet.getInt("customer_id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("phone"),
            resultSet.getString("address"),
            resultSet.getString("password")
    );


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
}