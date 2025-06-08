package DAO;

import model.EWalletPayment;
import exception.DatabaseException;
import java.sql.*;

public class EWalletTopUpDAO {
    private Connection conn;

    public EWalletTopUpDAO(Connection conn) {
        this.conn = conn;
    }

    public void addTopUp(int customerId, double amount, String description) throws DatabaseException {
        String sql = "INSERT INTO EWalletTopUp (customer_id, amount, description, status) VALUES (?, ?, ?, 'success')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setDouble(2, amount);
            stmt.setString(3, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menambahkan top-up e-wallet", e);
        }
    }
}       