package services;

import entities.Order;
import entities.OrderProduct;
import entities.Product;
import factories.RepositoryFactory;
import factories.ServiceFactory;
import repositories.CustomerRepository;
import repositories.OrderProductRepository;
import repositories.OrderRepository;
import viewmodels.OrderSummary;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderRepository orderRepository = RepositoryFactory.getOrderRepository();
    private final OrderProductRepository orderProductRepository = RepositoryFactory.getOrderProductRepository();
    private final CustomerRepository customerRepository = RepositoryFactory.getCustomerRepository();
    private final ProductService productService = ServiceFactory.getProductService();

    // #################### [ Read Operations ] ####################


    // #################### [ Order Logic ] ####################


    public int createOrderFromCart(int customerId,
                                   Map<Product, Integer> cartItems) throws SQLException {
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot create order.");
        }

        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer does not exist.");
        }

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName() + " " +
                        "(Available: " + product.getStockQuantity() + ", Requested: " + quantity + ")");
            }

            OrderProduct orderProduct = new OrderProduct(
                    product.getProductId(),
                    quantity,
                    product.getPrice()
            );

            orderProducts.add(orderProduct);
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDateTime.now());

        int orderId = orderRepository.addOrderAndReturnId(order);

        orderProducts.forEach(op -> op.setOrderId(orderId));
        orderProductRepository.addBatch(orderProducts);

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            productService.decreaseStock(entry.getKey().getProductId(), entry.getValue());
        }

        return orderId;
    }

    public List<OrderSummary> getSummariesByCustomerId(int customerId) throws SQLException {
        return orderRepository.getSummariesByCustomerId(customerId);
    }
}