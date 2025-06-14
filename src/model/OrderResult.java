package model;

public class OrderResult {
    private boolean success;
    private String message;
    private Order order;
    
    public OrderResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public OrderResult(boolean success, String message, Order order) {
        this.success = success;
        this.message = message;
        this.order = order;
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Order getOrder() { return order; }
}