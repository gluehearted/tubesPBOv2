package service;

import DAO.CartDAO;
import DAO.CartDAOImpl;
import model.User;
import model.MenuItem;
import model.CartItem;
import exception.DatabaseException;
import java.sql.Connection;
import java.util.List;

public class CustomerService {
    private CartDAO cartDAO;

    public CustomerService(Connection conn) {
        this.cartDAO = new CartDAOImpl();
    }

    public void addToCart(User user, MenuItem item, int quantity) throws DatabaseException {
        if (!user.isCustomer()) {
            throw new DatabaseException("Hanya pelanggan yang dapat menambah item ke keranjang");
        }
        cartDAO.addItemToCart(user.getId(), item.getId(), quantity);
        user.getCart().addItem(item);
    }

    public void removeFromCart(User user, MenuItem item) throws DatabaseException {
        if (!user.isCustomer()) {
            throw new DatabaseException("Hanya pelanggan yang dapat menghapus item dari keranjang");
        }
        cartDAO.removeItemFromCart(user.getId(), item.getId());
        user.getCart().removeItem(item);
    }

    public List<CartItem> getCartItems(User user) throws DatabaseException {
        if (!user.isCustomer()) {
            throw new DatabaseException("Hanya pelanggan yang dapat melihat keranjang");
        }
        return cartDAO.getCartItems(user.getId());
    }
}