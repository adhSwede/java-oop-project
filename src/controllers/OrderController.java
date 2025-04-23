package controllers;

import contexts.SessionContext;
import factories.ServiceFactory;
import services.OrderService;
import services.ProductService;
import utils.ConsoleHelper;
import viewmodels.OrderSummary;

import java.sql.SQLException;
import java.util.List;

public class OrderController {
    private final OrderService orderService = ServiceFactory.getOrderService();
    private final ProductService productService = ServiceFactory.getProductService();

    public void viewCustomerOrderHistory() throws SQLException {
        if (SessionContext.isGuest()) {
            ConsoleHelper.printWarning("You must be logged in to view your order history.");
            return;
        }

        int customerId = SessionContext
                .getCurrentCustomer()
                .getUserId();
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