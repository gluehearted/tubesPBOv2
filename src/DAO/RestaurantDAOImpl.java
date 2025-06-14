package DAO;

import model.Restaurant;
import model.MenuItem;
import exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAOImpl implements RestaurantDAO {
    private Connection connection;
    
    public RestaurantDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void save(Restaurant restaurant) throws DatabaseException {
        String sql = "INSERT INTO Restaurants (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
    
    @Override
    public void saveMenuItem(MenuItem menuItem) throws DatabaseException {
        String sql = "INSERT INTO MenuItems (name, price, restaurant_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
    
    @Override
    public List<Restaurant> getAllRestaurants() throws DatabaseException {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM Restaurants";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                restaurants.add(new Restaurant(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengambil daftar restoran", e);
        }
        return restaurants;
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
                    restaurantId,
                    restaurant
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mengambil menu items", e);
        }
        return menuItems;
    }
} 