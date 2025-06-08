package DAO;

import model.User;
import exception.DatabaseException;
import exception.RegistrationException;
import java.sql.*;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public void registerCustomer(User user) throws DatabaseException, RegistrationException {
        if (!user.isCustomer()) {
            throw new RegistrationException("Role pengguna harus 'customer'");
        }
        String sql = "INSERT INTO User (username, password, role, ewalletBalance, rekeningBalance) VALUES (?, ?, ?, 0.0, 0.0)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
                String cartSql = "INSERT INTO Cart (customer_id) VALUES (?)";
                try (PreparedStatement cartStmt = conn.prepareStatement(cartSql)) {
                    cartStmt.setInt(1, user.getId());
                    cartStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal mendaftarkan pelanggan", e);
        }
    }

    public User findCustomerById(int id) throws DatabaseException {
        String sql = "SELECT * FROM User WHERE id = ? AND role = 'customer'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
            throw new DatabaseException("Gagal mengambil data pelanggan", e);
        }
        return null;
    }
}