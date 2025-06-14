package controller;

import java.util.logging.Logger;
import service.AuthService;
import exception.DatabaseException;
import javax.swing.JOptionPane;
import model.User;

/**
 * Controller untuk mengelola operasi CRUD akun customer.
 */
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    public User getUserByUsername(String username) throws DatabaseException {
        return authService.getUserByUsername(username);
    }

    public void updateCustomer(String username, String password, double ewalletBalance, double rekeningBalance) throws DatabaseException {
        if (username == null || username.trim().isEmpty()) {
            throw new DatabaseException("Username tidak boleh kosong");
        }
        if (ewalletBalance < 0 || rekeningBalance < 0) {
            throw new DatabaseException("Saldo tidak boleh negatif");
        }
        authService.updateCustomer(username, password, ewalletBalance, rekeningBalance);
        LOGGER.info("Customer updated: " + username);
    }
}