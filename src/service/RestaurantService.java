package service;

import DAO.MenuItemDAO;
import model.Restaurant;
import model.MenuItem;
import exception.DatabaseException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class RestaurantService {
    private List<Restaurant> restaurants;
    private MenuItemDAO menuItemDAO;

    public RestaurantService(Connection conn, List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        this.menuItemDAO = new MenuItemDAO(conn);
    }

    public Optional<Restaurant> findRestaurantByName(String name) {
        return restaurants.stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<MenuItem> findMenuItemByName(Restaurant restaurant, String menuName) throws DatabaseException {
        List<MenuItem> menuItems = menuItemDAO.getMenuItemsByRestaurant(restaurant.getId());
        return menuItems.stream()
                .filter(m -> m.getName().equalsIgnoreCase(menuName))
                .findFirst();
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }
}