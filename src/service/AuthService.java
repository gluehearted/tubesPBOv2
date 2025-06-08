package service;

import dao.UserDAO;
import model.User;

import java.sql.Connection;

public class AuthService {
    private UserDAO userDAO;

    public AuthService(Connection conn) {
        this.userDAO = new UserDAO(conn);
    }

    public User login(String username, String password) {
        try {
            return userDAO.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerCustomer(User user) {
        try {
            return userDAO.registerCustomer(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
