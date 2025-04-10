package repositories;

import entities.Order;
import utils.SqlUtils;

import java.sql.*;

public class OrderRepository {

    public int addOrderAndReturnId(Order order) throws SQLException {
        String query = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        return SqlUtils.executeInsertAndReturnId(query, order.getCustomerId(), order.getOrderDate().toString());
    }
}