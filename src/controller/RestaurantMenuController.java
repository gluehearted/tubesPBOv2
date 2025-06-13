/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import java.util.logging.Logger;
import model.Restaurant;
import model.MenuItem;
import service.RestaurantService;
import exception.DatabaseException;
import javax.swing.JOptionPane;

/**
 * Controller untuk mengelola operasi CRUD restaurant dan menu.
 */
public class RestaurantMenuController {
    private static final Logger LOGGER = Logger.getLogger(RestaurantMenuController.class.getName());
    private final RestaurantService restaurantService;

    public RestaurantMenuController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public void addRestaurant(String name) {
        try {
            restaurantService.addRestaurant(name);
            LOGGER.info("Restaurant added: " + name);
            JOptionPane.showMessageDialog(null, "Restaurant berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (DatabaseException e) {
            LOGGER.severe("Failed to add restaurant: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal menambahkan restaurant: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addMenuItem(String name, double price, int restaurantId) {
        try {
            restaurantService.addMenuItem(name, price, restaurantId);
            LOGGER.info("Menu item added: " + name);
            JOptionPane.showMessageDialog(null, "Menu item berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (DatabaseException e) {
            LOGGER.severe("Failed to add menu item: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal menambahkan menu item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Restaurant> getAllRestaurants() {
        try {
            return restaurantService.getAllRestaurants();
        } catch (DatabaseException e) {
            LOGGER.severe("Failed to retrieve restaurants: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal memuat restaurant: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }

    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) {
        try {
            return restaurantService.getMenuItemsByRestaurant(restaurantId);
        } catch (DatabaseException e) {
            LOGGER.severe("Failed to retrieve menu items: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal memuat menu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }
}
