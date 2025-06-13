package model;

import java.util.List;

public class Order {
    private User user; // diganti dari Customer
    private List<MenuItem> items;
    private double totalAmount;
    private OrderStatus status;

    public Order(User user, List<MenuItem> items, double totalAmount) {
        this.user = user;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
    }

    public User getUser() { return user; }
    public List<MenuItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public enum OrderStatus {
        PENDING, PROCESSING, COMPLETED, CANCELLED
    }
}
