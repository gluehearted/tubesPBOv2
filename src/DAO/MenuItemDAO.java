package DAO;

import model.MenuItem;
import model.Restaurant;
import exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    private Connection conn;

    public MenuItemDAO(Connection conn) {
        this.conn = conn;
    }

    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) throws DatabaseException {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT m.id, m.name, m.price, r.id AS restaurant_id, r.name AS restaurant_name " +
                     "FROM MenuItem m JOIN Restaurant r ON m.restaurant_id = r.id " +
                     "WHERE m.restaurant_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"));
                MenuItem item = new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), restaurant);
                menuItems.add(item);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengambil daftar menu", e);
        }
        return menuItems;
    }

    public void addMenuItem(MenuItem item) throws DatabaseException {
        String sql = "INSERT INTO MenuItem (name, price, restaurant_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getRestaurant().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menambahkan menu", e);
        }
    }
}