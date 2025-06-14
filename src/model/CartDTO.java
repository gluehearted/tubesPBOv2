package model;

import java.util.List;

public class CartDTO {
    private Cart<MenuItem> cart;
    private List<CartItem> items;
    private double total;
    
    public CartDTO(Cart<MenuItem> cart, List<CartItem> items, double total) {
        this.cart = cart;
        this.items = items;
        this.total = total;
    }
    
    // Getters and setters
    public Cart<MenuItem> getCart() { return cart; }
    public int getCartId() {
        return cart.getCartId();
    }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public int getTotalItems() { 
        return items.stream().mapToInt(CartItem::getQuantity).sum(); 
    }
} 
