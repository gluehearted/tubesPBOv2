package DAO;

import model.MenuItem;
import model.Restaurant;
import exception.DatabaseException;
import db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAOImpl implements MenuItemDAO {
    private Connection connection;
    
    public MenuItemDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public MenuItemDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public MenuItem findById(int id) {
        String sql = "SELECT m.*, r.name as restaurant_name FROM MenuItems m " +
                    "JOIN Restaurants r ON m.restaurant_id = r.id " +
                    "WHERE m.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"));
                return new MenuItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("restaurant_id"),
                    restaurant
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding menu item by id: " + id, e);
        }
        return null;
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) throws DatabaseException {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT m.*, r.name as restaurant_name FROM MenuItems m " +
                    "JOIN Restaurants r ON m.restaurant_id = r.id " +
                    "WHERE m.restaurant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurant_id"), rs.getString("restaurant_name"));
                menuItems.add(new MenuItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("restaurant_id"),
                    restaurant
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengambil daftar menu", e);
        }
        return menuItems;
    }

    @Override
    public void addMenuItem(MenuItem item) throws DatabaseException {
        String sql = "INSERT INTO MenuItems (name, price, restaurant_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getRestaurantId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menambahkan menu", e);
        }
    }
} 