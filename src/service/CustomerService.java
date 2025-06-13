package service;

import DAO.CartDAO;
import model.User;
import model.MenuItem;
import exception.DatabaseException;
import java.sql.Connection;
import java.util.List;

public class CustomerService {
    private CartDAO cartDAO;

    public CustomerService(Connection conn) {
        this.cartDAO = new CartDAO(conn);
    }

    public void addToCart(User user, MenuItem item, int quantity) throws DatabaseException {
        if (!user.isCustomer()) {
            throw new DatabaseException("Hanya pelanggan yang dapat menambah item ke keranjang");
        }
        cartDAO.addToCart(user.getId(), item.getId(), quantity);
        user.getCart().addItem(item);
    }

    public void removeFromCart(User user, MenuItem item) throws DatabaseException {
        if (!user.isCustomer()) {
            throw new DatabaseException("Hanya pelanggan yang dapat menghapus item dari keranjang");
        }
        cartDAO.removeFromCart(user.getId(), item.getId());
        user.getCart().removeItem(item);
    }

    public List<MenuItem> getCartItems(User user) throws DatabaseException {
        if (!user.isCustomer()) {
            throw new DatabaseException("Hanya pelanggan yang dapat melihat keranjang");
        }
        return cartDAO.getCartItems(user.getId());
    }
}