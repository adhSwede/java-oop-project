package viewmodels;

import java.time.LocalDate;

public class OrderSummary {
    private int orderId;
    private int customerId;
    private String customerName;
    private LocalDate orderDate;

    public OrderSummary(int orderId, int customerId, String customerName, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderDate = orderDate;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Customer: " + customerName +
                " (ID: " + customerId + ")" +
                ", Date: " + orderDate;
    }
}
