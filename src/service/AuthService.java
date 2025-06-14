/*
 * Authentication Service
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import model.User;
import DAO.UserDAO;
import exception.AuthenticationException;
import exception.RegistrationException;
import exception.DatabaseException;

public class AuthService {
    private final UserDAO userDao;

    public AuthService(Connection connection) {
        this.userDao = new UserDAO(connection);
    }

    public User login(String username, String password) throws AuthenticationException, DatabaseException {
        User user = userDao.findByUsername(username);
    
        if (user == null) {
            throw new AuthenticationException("User tidak ditemukan");
        }
    
        String role = user.getRole();
    
        if ("customer".equalsIgnoreCase(role)) {
            // Cek hash password dengan BCrypt
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new AuthenticationException("Password customer salah");
            }
        } else if ("admin".equalsIgnoreCase(role)) {
            // Admin hanya cocokkan plain password  
            if (!"adminpass".equals(password)) {
                throw new AuthenticationException("Password admin salah");
            }
        } else {
            throw new AuthenticationException("Role tidak dikenali");
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
        user.setEwalletBalance(1_000_000);
        user.setRekeningBalance(1_000_000);
        userDao.registerCustomer(user);
    }

    public void updateCustomer(String username, String password, double ewalletBalance, double rekeningBalance) throws DatabaseException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new DatabaseException("User tidak ditemukan");
        }
        if (!"customer".equalsIgnoreCase(user.getRole())) {
            throw new DatabaseException("Hanya akun customer yang dapat diedit");
        }
        String hashedPassword = password.isEmpty() ? user.getPassword() : BCrypt.hashpw(password, BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        user.setEwalletBalance(ewalletBalance);
        user.setRekeningBalance(rekeningBalance);
        userDao.update(user);
    }

    // In AuthService.java
    public void updateUser(User user) throws DatabaseException {
        userDao.update(user);
    }

    public User getUserByUsername(String username) throws DatabaseException {
        return userDao.findByUsername(username);
    }
}