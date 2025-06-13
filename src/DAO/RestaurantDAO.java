package DAO;

import model.Restaurant;
import exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MenuItem;

public class RestaurantDAO {
    private Connection conn;

    public RestaurantDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(Restaurant restaurant) throws DatabaseException {
        String query = "INSERT INTO Restaurants (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, restaurant.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                restaurant.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menyimpan restaurant", e);
        }
    }

    public void saveMenuItem(MenuItem menuItem) throws DatabaseException {
        String query = "INSERT INTO MenuItems (name, price, restaurant_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, menuItem.getName());
            stmt.setDouble(2, menuItem.getPrice());
            stmt.setInt(3, menuItem.getRestaurantId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                menuItem.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menyimpan menu item", e);
        }
    }

    public List<Restaurant> getAllRestaurants() throws DatabaseException {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM Restaurants";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                restaurants.add(new Restaurant(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengambil daftar restoran", e);
        }
        return restaurants;
    }

    public void addRestaurant(Restaurant restaurant) throws DatabaseException {
        String sql = "INSERT INTO Restaurants (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, restaurant.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                restaurant.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menambahkan restoran", e);
        }
    }

    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) throws DatabaseException {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM MenuItems WHERE restaurant_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                menuItems.add(new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), restaurantId, null));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal memuat menu items", e);
        }
        return menuItems;
    }
}