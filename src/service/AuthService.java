package service;

import DAO.UserDAO;
import model.User;
import exception.AuthenticationException;
import exception.RegistrationException;
import exception.DatabaseException;
import java.sql.Connection;

public class AuthService {
    private UserDAO userDAO;

    public AuthService(Connection conn) {
        this.userDAO = new UserDAO(conn);
    }

    public User login(String username, String password) throws AuthenticationException, DatabaseException {
        return userDAO.login(username, password);
    }

    public void registerCustomer(User user) throws RegistrationException, DatabaseException {
        userDAO.registerCustomer(user);
    }
}