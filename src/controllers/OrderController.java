package controllers;

import entities.OrderProduct;
import entities.Product;

import factories.ServiceFactory;
import services.OrderService;
import services.ProductService;

import utils.ConsoleHelper;
import utils.ExceptionHandler;
import utils.InputUtils;

import viewmodels.OrderSummary;

import contexts.SessionContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService = ServiceFactory.getOrderService();
    private final ProductService productService = ServiceFactory.getProductService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.printHeader("📦 Order Menu");
            ConsoleHelper.printOption("1", "Place new order");
            ConsoleHelper.printOption("2", "View customer order history");
            ConsoleHelper.printOption("B", "Back to previous menu");
            ConsoleHelper.prompt("Select an option: ");

            select = sc.nextLine().trim().toLowerCase();

            switch (select) {
                case "1" -> placeOrder();
                case "2" -> viewCustomerOrderHistory();
                case "b" -> ConsoleHelper.printSuccess("Returning to main menu...");
                default -> ConsoleHelper.printWarning("Invalid option. Please try again.");
            }

        } while (!select.equals("b"));
    }

    public void placeOrder() {
        Scanner sc = new Scanner(System.in);

        if (SessionContext.isGuest()) {
            ConsoleHelper.printWarning("You must be logged in to do this.");
            return;
        }

        try {
            int customerId = SessionContext.getCurrentCustomer().getUserId();
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
            ConsoleHelper.printSuccess("✅ Order placed successfully. Order ID: " + orderId);

        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleUserError(e, "placing the order");
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "placing the order");
        }

        ConsoleHelper.printDivider();
    }

    public void viewCustomerOrderHistory() throws SQLException {
        if (SessionContext.isGuest()) {
            ConsoleHelper.printWarning("You must be logged in to view your order history.");
            return;
        }

        int customerId = SessionContext.getCurrentCustomer().getUserId();
        List<OrderSummary> summaries = orderService.getSummariesByCustomerId(customerId);

        ConsoleHelper.printHeader("📋 Your Orders");
        if (summaries.isEmpty()) {
            ConsoleHelper.printWarning("No orders found.");
        } else {
            ConsoleHelper.printList(summaries);
        }
        ConsoleHelper.printDivider();
    }
}