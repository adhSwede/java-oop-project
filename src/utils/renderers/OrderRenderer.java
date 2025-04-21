package utils.renderers;

import entities.Order;
import entities.users.Customer;
import utils.ConsoleHelper;
import viewmodels.OrderSummary;

import static constants.Colors.*;

public class OrderRenderer {

    public static void printOrderSummary(Order o, Customer c) {
        System.out.println(BOLD + "📦 Order ID: " + o.getOrderId() + RESET);
        System.out.println("👤 Customer: " + c.getName() + " (ID: " + c.getUserId() + ")");
        System.out.println("📅 Date: " + o.getOrderDate());
        ConsoleHelper.printDivider();
    }

    public static void printOrderSummary(OrderSummary summary) {
        System.out.println(BOLD + "📦 Order ID: " + summary.getOrderId() + RESET);
        System.out.println("👤 Customer: " + summary.getCustomerName() + " (ID: " + summary.getCustomerId() + ")");
        System.out.println("📅 Date: " + summary.getOrderDate());
        System.out.println("💰 Total: $" + String.format("%.2f", summary.getTotal()));
        ConsoleHelper.printDivider();
    }
}
