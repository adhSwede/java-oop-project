package viewmodels;

import java.time.LocalDateTime;

public class OrderSummary {
    private final int orderId;
    private final int customerId;
    private final String customerName;
    private final LocalDateTime orderDate;
    private final double total;

    public OrderSummary(int orderId,
                        int customerId,
                        String customerName,
                        LocalDateTime orderDate,
                        double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Customer: " + customerName + " (ID: " + customerId + ")" +
                ", Date: " + orderDate +
                ", Total: $" + String.format("%.2f", total);
    }
}