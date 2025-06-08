package service;

import model.*;

import java.util.List;
import java.util.Optional;

public class RestaurantService {

    private List<Restaurant> restaurants;

    public RestaurantService(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Optional<Restaurant> findRestaurantByName(String name) {
        return restaurants.stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<MenuItem> findMenuItemByName(Restaurant restaurant, String menuName) {
        return restaurant.getMenu().stream()
                .filter(m -> m.getName().equalsIgnoreCase(menuName))
                .findFirst();
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }
}