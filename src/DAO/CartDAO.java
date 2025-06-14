package DAO;

import model.MenuItem;
import model.CartItem;
import model.Cart;
import java.util.List;

public interface CartDAO {
    Cart<MenuItem> getCartByCustomerId(int customerId);
    boolean addItemToCart(int cartId, int menuItemId, int quantity);
    boolean updateCartItemQuantity(int cartId, int menuItemId, int quantity);
    boolean removeItemFromCart(int cartId, int menuItemId);
    boolean clearCart(int cartId);
    List<CartItem> getCartItems(int cartId);
    double calculateCartTotal(int cartId);
}