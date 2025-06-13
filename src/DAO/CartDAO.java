package DAO;

import model.MenuItem;
import model.Restaurant;
import exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Connection conn;

    public CartDAO(Connection conn) {
        this.conn = conn;
    }

    public void addToCart(int customerId, int menuItemId, int quantity) throws DatabaseException {
        String sql = "INSERT INTO Cart_MenuItems (cart_id, menu_item_id, quantity) " +
                     "VALUES ((SELECT id FROM Cart WHERE customer_id = ?), ?, ?) " +
                     "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, menuItemId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menambahkan item ke keranjang", e);
        }
    }

    public void removeFromCart(int customerId, int menuItemId) throws DatabaseException {
        String sql = "DELETE FROM Cart_MenuItems WHERE cart_id = (SELECT id FROM Cart WHERE customer_id = ?) AND menu_item_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, menuItemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menghapus item dari keranjang", e);
        }
    }

    public List<MenuItem> getCartItems(int customerId) throws DatabaseException {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT m.id, m.name, m.price, m.restaurant_id, r.name AS restaurant_name, cm.quantity " +
                     "FROM Cart_MenuItems cm " +
                     "JOIN Cart c ON cm.cart_id = c.id " +
                     "JOIN MenuItems m ON cm.menu_item_id = m.id " +
                     "JOIN Restaurants r ON m.restaurant_id = r.id " +
                     "WHERE c.customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"));
                MenuItem item = new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), restaurant.getId(), restaurant);
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengambil item keranjang", e);
        }
        return items;
    }

    public void clearCart(int customerId) throws DatabaseException {
        String sql = "DELETE FROM Cart_MenuItems WHERE cart_id = (SELECT id FROM Cart WHERE customer_id = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengosongkan keranjang", e);
        }
    }
}