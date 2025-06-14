package service;

import DAO.CartDAO;
import DAO.CartDAOImpl;
import DAO.MenuItemDAO;
import DAO.MenuItemDAOImpl;
import DAO.UserDAO;
import model.Cart;
import model.CartDTO;
import model.CartItem;
import model.MenuItem;
import model.Order;
import model.OrderResult;
import model.User;
import db.DatabaseConnection;
import java.util.List;
import java.util.ArrayList;

public class CartService {
    private CartDAO cartDAO;
    private MenuItemDAO menuItemDAO;
    private UserDAO userDAO;
    
    public CartService() {
        try {
            this.cartDAO = new CartDAOImpl();
            this.menuItemDAO = new MenuItemDAOImpl();
            this.userDAO = new UserDAO(DatabaseConnection.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public CartDTO getCartForCustomer(int customerId) {
        Cart<MenuItem> cart = cartDAO.getCartByCustomerId(customerId);
        List<CartItem> items = cartDAO.getCartItems(cart.getCartId());
        double total = cartDAO.calculateCartTotal(cart.getCartId());
        
        return new CartDTO(cart, items, total);
    }
    
    public boolean addItemToCart(int customerId, int menuItemId, int quantity) {
        MenuItem menuItem = menuItemDAO.findById(menuItemId);
        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item not found");
        }
        
        Cart<MenuItem> cart = cartDAO.getCartByCustomerId(customerId);
        return cartDAO.addItemToCart(cart.getCartId(), menuItemId, quantity);
    }
    
    public boolean updateItemQuantity(int customerId, int menuItemId, int newQuantity) {
        if (newQuantity <= 0) {
            return removeItemFromCart(customerId, menuItemId);
        }
        
        Cart<MenuItem> cart = cartDAO.getCartByCustomerId(customerId);
        return cartDAO.updateCartItemQuantity(cart.getCartId(), menuItemId, newQuantity);
    }
    
    public boolean removeItemFromCart(int customerId, int menuItemId) {
        Cart<MenuItem> cart = cartDAO.getCartByCustomerId(customerId);
        return cartDAO.removeItemFromCart(cart.getCartId(), menuItemId);
    }
    
    public boolean clearCart(int customerId) {
        Cart<MenuItem> cart = cartDAO.getCartByCustomerId(customerId);
        return cartDAO.clearCart(cart.getCartId());
    }
    
    public OrderResult checkout(int customerId, String paymentMethod) {
        CartDTO cartDTO = getCartForCustomer(customerId);
        
        if (cartDTO.getItems().isEmpty()) {
            return new OrderResult(false, "Cart is empty");
        }
        
        User customer = userDAO.findById(customerId);
        double total = cartDTO.getTotal();
        
        // Validate payment
        if (!validatePayment(customer, paymentMethod, total)) {
            return new OrderResult(false, "Insufficient balance");
        }
        
        try {
            // Process payment
            processPayment(customer, paymentMethod, total);
            
            // Create order
            Order order = createOrder(customer, cartDTO, paymentMethod);
            
            // Clear cart after successful order
            clearCart(customerId);
            
            return new OrderResult(true, "Order created successfully", order);
            
        } catch (Exception e) {
            return new OrderResult(false, "Checkout failed: " + e.getMessage());
        }
    }
    
    private boolean validatePayment(User customer, String paymentMethod, double amount) {
        switch (paymentMethod.toLowerCase()) {
            case "ewallet":
                return customer.getEwalletBalance() >= amount;
            case "debit":
                return customer.getRekeningBalance() >= amount;
            default:
                return false;
        }
    }
    
    private void processPayment(User customer, String paymentMethod, double amount) {
        switch (paymentMethod.toLowerCase()) {
            case "ewallet":
                customer.setEwalletBalance(customer.getEwalletBalance() - amount);
                break;
            case "debit":
                customer.setRekeningBalance(customer.getRekeningBalance() - amount);
                break;
        }
        userDAO.update(customer);
    }
    
    private Order createOrder(User customer, CartDTO cartDTO, String paymentMethod) {
        List<MenuItem> orderItems = new ArrayList<>();
        for (CartItem item : cartDTO.getItems()) {
            MenuItem menuItem = item.getMenuItem();
            menuItem.setQuantity(item.getQuantity());
            orderItems.add(menuItem);
        }
        return new Order(customer, orderItems, cartDTO.getTotal());
    }
}