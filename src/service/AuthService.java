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
        try {
            User user = userDao.findUserByU sername(username);
            if (user == null) {
                throw new AuthenticationException("User not found");
            }
            // Verify password against stored hash
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new AuthenticationException("Invalid password");
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException("Login error: " + e.getMessage());
        }
    }

    public void registerCustomer(User user) throws RegistrationException, DatabaseException {
        try {
            // Check if username exists
            if (userDao.findUserByUsername(user.getUsername()) != null) {
                throw new RegistrationException("Username already exists");
            }
            // Hash password with BCrypt
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)); // 12 is log rounds
            // Update user with hashed password
            user.setPassword(hashedPassword);
            userDao.registerUser(user);
        } catch (SQLException e) {
            throw new DatabaseException("Registration error: " + e.getMessage());
        }
    }
}