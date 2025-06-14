package service;

import DAO.RestaurantDAO;
import DAO.MenuItemDAO;
import model.Restaurant;
import model.MenuItem;
import exception.DatabaseException;
import java.sql.Connection;
import java.util.List;

public class AdminService {
    private RestaurantDAO restaurantDAO;
    private MenuItemDAO menuItemDAO;

    public AdminService(Connection conn) {
        this.restaurantDAO = new RestaurantDAO(conn);
        this.menuItemDAO = new MenuItemDAO(conn);
    }

    public void addRestaurant(Restaurant restaurant) throws DatabaseException {
        restaurantDAO.save(restaurant);
    }

    public void addMenuItem(MenuItem menuItem) throws DatabaseException {
        menuItemDAO.addMenuItem(menuItem);
    }

    public List<Restaurant> getAllRestaurants() throws DatabaseException {
        return restaurantDAO.getAllRestaurants();
    }
}