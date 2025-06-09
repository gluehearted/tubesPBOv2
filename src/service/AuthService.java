/*
 * Authentication Service
 */
package service;

import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;
import model.User;
import DAO.UserDAO;
import exception.AuthenticationException;
import exception.RegistrationException;
import exception.DatabaseException;
import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDao;

    public AuthService(Connection connection) {
        this.userDao = new UserDAO(connection);
    }

    public User login(String username, String password) throws AuthenticationException, DatabaseException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("User not found");
        }
        // Verify password against stored hash
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }
        return user;
    }

    public void registerCustomer(User user) throws RegistrationException, DatabaseException {
        // Check if username exists
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new RegistrationException("Username already exists");
        }
        // Hash password with BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)); // 12 is log rounds
        // Update user with hashed password
        user.setPassword(hashedPassword);
        userDao.registerCustomer(user);
    }
}