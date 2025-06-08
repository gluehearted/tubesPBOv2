package DAO;

import model.User;
import exception.AuthenticationException;
import exception.DatabaseException;
import exception.RegistrationException;
import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public User findByUsername(String username) throws DatabaseException {
        String query = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getDouble("ewalletBalance"),
                    rs.getDouble("rekeningBalance")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mencari pengguna", e);
        }
        return null;
    }

    public void save(User user) throws DatabaseException {
        String query = "INSERT INTO User (username, password, role, ewalletBalance, rekeningBalance) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setDouble(4, user.getEwalletBalance());
            stmt.setDouble(5, user.getRekeningBalance());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menyimpan pengguna", e);
        }
    }

    public User login(String username, String password) throws AuthenticationException, DatabaseException {
        User user = findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException("Username atau password salah");
        }
        return user;
    }

    public void registerCustomer(User user) throws RegistrationException, DatabaseException {
        if (findByUsername(user.getUsername()) != null) {
            throw new RegistrationException("Username sudah digunakan");
        }
        if (!user.isCustomer()) {
            throw new RegistrationException("Role harus 'customer' untuk registrasi pelanggan");
        }
        save(user);
        String cartSql = "INSERT INTO Cart (customer_id) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(cartSql)) {
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Gagal membuat keranjang untuk pelanggan", e);
        }
    }
}