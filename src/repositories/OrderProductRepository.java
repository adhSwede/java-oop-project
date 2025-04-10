package repositories;

import entities.OrderProduct;
import utils.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderProductRepository {

    public void addOrderProduct(OrderProduct orderProduct) throws SQLException {
        String query = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        SqlUtils.executeUpdate(
                query,
                orderProduct.getOrderId(),
                orderProduct.getProductId(),
                orderProduct.getQuantity(),
                orderProduct.getUnitPrice()
        );
    }

    @SuppressWarnings("SqlResolve")
    public void addBatch(List<OrderProduct> orderProducts) throws SQLException {
        String query = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = SqlUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (OrderProduct op : orderProducts) {
                stmt.setInt(1, op.getOrderId());
                stmt.setInt(2, op.getProductId());
                stmt.setInt(3, op.getQuantity());
                stmt.setDouble(4, op.getUnitPrice());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}