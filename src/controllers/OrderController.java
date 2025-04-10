package controllers;

import entities.OrderProduct;
import entities.Product;
import services.OrderService;
import services.ProductService;
import utils.ConsoleHelper;
import utils.ExceptionHandler;
import utils.InputUtils;
import viewmodels.OrderSummary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    OrderService orderService = new OrderService();
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.clearConsole();
            System.out.println("\n--- Order Menu ---");
            System.out.println("1. Place new order");
            System.out.println("2. View customer order history");
            System.out.println("B. Back to main menu");

            select = sc.nextLine().trim().toLowerCase();

            switch (select) {
                case "1" -> placeOrder();
                case "2" -> viewCustomerOrderHistory();
                case "b" -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!select.equals("b"));
    }

    public void placeOrder() {
        Scanner sc = new Scanner(System.in);

        try {
            int customerId = InputUtils.promptPositiveInt(sc, "Enter customer ID: ");
            int productId = InputUtils.promptPositiveInt(sc, "Enter product ID: ");
            int quantity = InputUtils.promptPositiveInt(sc, "Enter quantity: ");

            Product product = productService.getProductById(productId);

            if (product == null) {
                ExceptionHandler.handleUserError(
                        new IllegalArgumentException("Product not found."),
                        "placing the order"
                );
                return;
            }

            double price = product.getPrice();
            OrderProduct item = new OrderProduct(productId, quantity, price);
            List<OrderProduct> orderItems = new ArrayList<>();
            orderItems.add(item);

            int orderId = orderService.placeOrder(customerId, orderItems);
            System.out.println("✅ Order placed successfully. Order ID: " + orderId);

        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleUserError(e, "placing the order");
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "placing the order");
        }
    }

    public void viewCustomerOrderHistory() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int customerId = InputUtils.promptPositiveInt(sc, "Enter customer ID: ");
        List<OrderSummary> summaries = orderService.getSummariesByCustomerId(customerId);
        ConsoleHelper.printList(summaries);
    }
}
