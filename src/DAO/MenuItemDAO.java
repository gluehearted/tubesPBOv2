package DAO;

import model.MenuItem;
import exception.DatabaseException;
import java.util.List;

public interface MenuItemDAO {
    MenuItem findById(int id);
    List<MenuItem> getMenuItemsByRestaurant(int restaurantId) throws DatabaseException;
    void addMenuItem(MenuItem item) throws DatabaseException;
}