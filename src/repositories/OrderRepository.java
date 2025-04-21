package repositories;

import entities.Order;
import utils.SqlUtils;

import java.sql.*;

public class OrderRepository {

    public int addOrderAndReturnId(Order order) throws SQLException {
        String query = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        return SqlUtils.executeInsertAndReturnId(query, order.getCustomerId(), order.getOrderDate().toString());
    }

    public boolean hasCustomerPurchased(int customerId, int productId) throws SQLException {
        String query = """
        SELECT 1 FROM orders o
        JOIN orders_products op ON o.order_id = op.order_id
        WHERE o.customer_id = ? AND op.product_id = ?
        LIMIT 1
    """;
        return !SqlUtils.executeAndMap(query, rs -> 1,
                String.valueOf(customerId),
                String.valueOf(productId)
        ).isEmpty();
    }
}