package services;

import entities.Order;
import entities.OrderProduct;
import entities.Product;
import repositories.CustomerRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.OrderProductRepository;
import utils.Mappers;
import utils.SqlUtils;
import viewmodels.OrderSummary;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static utils.Mappers.orderMapper;

public class OrderService {
    OrderRepository orderRepository = new OrderRepository();
    ProductRepository productRepository = new ProductRepository();
    OrderProductRepository orderProductRepository = new OrderProductRepository();
    CustomerRepository customerRepository = new CustomerRepository();

    private void ensureStockAvailable(List<OrderProduct> orderProducts) throws SQLException {
        for (OrderProduct item : orderProducts) {
            Product product = productRepository.getById(item.getProductId());
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
        }
    }

    public int placeOrder(int customerId, List<OrderProduct> orderProducts) throws SQLException {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer does not exist.");
        }

        ensureStockAvailable(orderProducts);

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDate.now());

        int orderId = orderRepository.addOrderAndReturnId(order);

        orderProducts.forEach(op -> op.setOrderId(orderId));
        orderProductRepository.addBatch(orderProducts);

        return orderId;
    }

    public double calculateTotalPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .mapToDouble(op -> op.getQuantity() * op.getUnitPrice())
                .sum();
    }

    public List<OrderSummary> getSummariesByCustomerId(int customerId) throws SQLException {
        String query = """
        SELECT o.order_id, o.customer_id, o.order_date, c.name AS customer_name
        FROM orders o
        JOIN customers c ON o.customer_id = c.customer_id
        WHERE o.customer_id = ?
    """;
        return SqlUtils.executeAndMap(query, Mappers.orderSummaryMapper, String.valueOf(customerId));
    }
}