package DAO;

import model.Restaurant;
import exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    private Connection conn;

    public RestaurantDAO(Connection conn) {
        this.conn = conn;
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
}