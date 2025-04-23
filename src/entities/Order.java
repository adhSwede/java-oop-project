package entities;


import java.time.LocalDateTime;

public class Order {

    private int orderId;
    private int customerId;
    private LocalDateTime orderDate;

    // #################### [ Constructors ] ####################
    public Order() {
        // Empty constructor for empty orders.
    }

    public Order(int customerId,
                 LocalDateTime orderDate) {
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(int orderId,
                 int customerId,
                 LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    // #################### [ Getters & Setters ] ####################
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    // #################### [ ToString ] ####################
    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Customer ID: " + customerId + ", Date: " + orderDate;
    }
}
