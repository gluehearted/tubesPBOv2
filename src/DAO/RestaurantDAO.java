package DAO;

import model.Restaurant;
import model.MenuItem;
import exception.DatabaseException;
import java.util.List;

public interface RestaurantDAO {
    void save(Restaurant restaurant) throws DatabaseException;
    void saveMenuItem(MenuItem menuItem) throws DatabaseException;
    List<Restaurant> getAllRestaurants() throws DatabaseException;
    List<MenuItem> getMenuItemsByRestaurant(int restaurantId) throws DatabaseException;
}