package entities;

import java.time.LocalDate;

public class Order {
    private int orderId;
    private int customerId;
    private LocalDate orderDate;

    public Order() {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(int customerId, LocalDate orderDate) {
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(int orderId, int customerId, LocalDate orderDate) {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Customer ID: " + customerId + ", Date: " + orderDate;
    }
}