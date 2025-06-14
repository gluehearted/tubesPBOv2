package model;

public class CartItem {
    private MenuItem menuItem;
    private int quantity;
    
    public CartItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }
    
    // Getters and setters
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}