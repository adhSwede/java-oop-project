package repositories;

import entities.Order;
import utils.Mappers;
import utils.SqlUtils;
import viewmodels.OrderSummary;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderRepository {

    public int addOrderAndReturnId(Order order) throws SQLException {
        String query = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        return SqlUtils.executeInsertAndReturnId(query,
                order.getCustomerId(),
                Timestamp.valueOf(order.getOrderDate()));
    }

    public boolean hasCustomerPurchased(int customerId,
                                        int productId) throws SQLException {
        String query = """
                    SELECT 1 FROM orders o
                    JOIN orders_products op ON o.order_id = op.order_id
                    WHERE o.customer_id = ? AND op.product_id = ?
                    LIMIT 1
                """;
        return !SqlUtils
                .executeAndMap(query,
                        rs -> 1,
                        String.valueOf(customerId),
                        String.valueOf(productId))
                .isEmpty();
    }

    public List<OrderSummary> getSummariesByCustomerId(int customerId) throws SQLException {
        String query = """
                SELECT o.order_id, o.customer_id, o.order_date, c.name AS customer_name,
                               SUM(op.quantity * op.unit_price) AS total
                        FROM orders o
                        JOIN customers c ON o.customer_id = c.customer_id
                        JOIN orders_products op ON o.order_id = op.order_id
                        WHERE o.customer_id = ?
                        GROUP BY o.order_id
                """;
        return SqlUtils.executeAndMap(query,
                Mappers.orderSummaryMapper,
                String.valueOf(customerId));
    }
}