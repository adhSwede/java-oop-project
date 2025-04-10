package utils;

import entities.*;
import viewmodels.OrderSummary;

import java.time.LocalDate;

public class Mappers {
    public static final RowMapper<Category> categoryMapper = resultSet -> new Category(
            resultSet.getInt("category_id"),
            resultSet.getString("name")
    );

    public static final RowMapper<Customer> customerMapper = resultSet -> new Customer(
            resultSet.getInt("customer_id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("phone"),
            resultSet.getString("address"),
            resultSet.getString("password")
    );

    public static final RowMapper<OrderProduct> orderProductMapper = resultSet -> new OrderProduct(
            resultSet.getInt("order_product_id"),
            resultSet.getInt("order_id"),
            resultSet.getInt("product_id"),
            resultSet.getInt("quantity"),
            resultSet.getDouble("unit_price")
    );

    public static final RowMapper<Product> productMapper = resultSet -> new Product(
            resultSet.getInt("product_id"),
            resultSet.getInt("manufacturer_id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getDouble("price"),
            resultSet.getInt("stock_quantity")
    );

    public static final RowMapper<Order> orderMapper = resultSet -> new Order(
            resultSet.getInt("order_id"),
            resultSet.getInt("customer_id"),
            resultSet.getDate("order_date").toLocalDate()
    );

    public static final RowMapper<OrderSummary> orderSummaryMapper = resultSet -> new OrderSummary(
            resultSet.getInt("order_id"),
            resultSet.getInt("customer_id"),
            resultSet.getString("customer_name"),
            LocalDate.parse(resultSet.getString("order_date").split(" ")[0])
    );
}
