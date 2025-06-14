package DAO;

import model.MenuItem;
import model.Restaurant;
import model.CartItem;
import model.Cart;
import db.DatabaseConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CartDAOImpl implements CartDAO {
    private Connection connection;
    
    public CartDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    @Override
    public Cart<MenuItem> getCartByCustomerId(int customerId) {
        String sql = "SELECT * FROM Carts WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cart<MenuItem> cart = new Cart<>();
                cart.setId(rs.getInt("id"));
                return cart;
            }
            // Create new cart if doesn't exist
            return createNewCart(customerId);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting cart for customer: " + customerId, e);
        }
    }
    
    private Cart<MenuItem> createNewCart(int customerId) {
        String sql = "INSERT INTO Carts (customer_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Cart<MenuItem> cart = new Cart<>();
                cart.setId(rs.getInt(1));
                return cart;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating cart for customer: " + customerId, e);
        }
        return null;
    }
    
    @Override
    public List<CartItem> getCartItems(int cartId) {
        String sql = "SELECT cmi.cart_id, cmi.menu_item_id, cmi.quantity, " +
                    "mi.name, mi.price, r.name as restaurant_name " +
                    "FROM Cart_MenuItems cmi " +
                    "JOIN MenuItems mi ON cmi.menu_item_id = mi.id " +
                    "JOIN Restaurants r ON mi.restaurant_id = r.id " +
                    "WHERE cmi.cart_id = ?";
        
        List<CartItem> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MenuItem menuItem = new MenuItem(
                    rs.getInt("menu_item_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("restaurant_id"),
                    new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"))
                );
                items.add(new CartItem(menuItem, rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting cart items for cart: " + cartId, e);
        }
        return items;
    }
    
    @Override
    public boolean addItemToCart(int cartId, int menuItemId, int quantity) {
        // Check if item already exists in cart
        String checkSql = "SELECT quantity FROM Cart_MenuItems WHERE cart_id = ? AND menu_item_id = ?";
        String updateSql = "UPDATE Cart_MenuItems SET quantity = quantity + ? WHERE cart_id = ? AND menu_item_id = ?";
        String insertSql = "INSERT INTO Cart_MenuItems (cart_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, cartId);
            checkStmt.setInt(2, menuItemId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Item exists, update quantity
                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, cartId);
                updateStmt.setInt(3, menuItemId);
                return updateStmt.executeUpdate() > 0;
            } else {
                // Item doesn't exist, insert new
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, cartId);
                insertStmt.setInt(2, menuItemId);
                insertStmt.setInt(3, quantity);
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding item to cart", e);
        }
    }
    
    @Override
    public double calculateCartTotal(int cartId) {
        String sql = "SELECT SUM(cmi.quantity * mi.price) as total " +
                    "FROM Cart_MenuItems cmi " +
                    "JOIN MenuItems mi ON cmi.menu_item_id = mi.id " +
                    "WHERE cmi.cart_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating cart total", e);
        }
        return 0.0;
    }

    @Override
    public boolean removeItemFromCart(int cartId, int menuItemId) {
        String sql = "DELETE FROM Cart_MenuItems WHERE cart_id = ? AND menu_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, menuItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error removing item from cart", e);
        }
    }

    @Override
    public boolean clearCart(int cartId) {
        String sql = "DELETE FROM Cart_MenuItems WHERE cart_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error clearing cart", e);
        }
    }

    @Override
    public boolean updateCartItemQuantity(int cartId, int menuItemId, int quantity) {
        String sql = "UPDATE Cart_MenuItems SET quantity = ? WHERE cart_id = ? AND menu_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, menuItemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating cart item quantity", e);
        }
    }
} 