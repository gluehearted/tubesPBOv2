package service;

import java.sql.Connection;
import java.util.List;
import model.Restaurant;
import model.MenuItem;
import DAO.RestaurantDAO;
import DAO.RestaurantDAOImpl;
import exception.DatabaseException;

/**
 * Service untuk mengelola operasi bisnis restaurant dan menu.
 */
public class RestaurantService {
    private final RestaurantDAO restaurantDAO;

    public RestaurantService(Connection connection) {
        this.restaurantDAO = new RestaurantDAOImpl(connection);
    }

    public void addRestaurant(String name) throws DatabaseException {
        if (name == null || name.trim().isEmpty()) {
            throw new DatabaseException("Nama restaurant tidak boleh kosong");
        }
        restaurantDAO.save(new Restaurant(0, name));
    }

    public void addMenuItem(String name, double price, int restaurantId) throws DatabaseException {
        if (name == null || name.trim().isEmpty()) {
            throw new DatabaseException("Nama menu tidak boleh kosong");
        }
        if (price <= 0) {
            throw new DatabaseException("Harga harus lebih dari 0");
        }
        restaurantDAO.saveMenuItem(new MenuItem(0, name, price, restaurantId, null));
    }

    public List<Restaurant> getAllRestaurants() throws DatabaseException {
        return restaurantDAO.getAllRestaurants();
    }

    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) throws DatabaseException {
        return restaurantDAO.getMenuItemsByRestaurant(restaurantId);
    }
}