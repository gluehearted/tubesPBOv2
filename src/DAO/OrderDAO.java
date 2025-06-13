package DAO;

import model.Order;
import model.MenuItem;
import exception.DatabaseException;
import java.sql.*;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Order order) throws DatabaseException {
        String sql = "INSERT INTO `Orders` (customer_id, totalAmount, payment_method) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUser().getId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setString(3, order.getTotalAmount() <= order.getUser().getEwalletBalance() ? "ewallet" : "debit");
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                for (MenuItem item : order.getItems()) {
                    String detailSql = "INSERT INTO Order_MenuItems (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement detailStmt = conn.prepareStatement(detailSql)) {
                        detailStmt.setInt(1, orderId);
                        detailStmt.setInt(2, item.getId());
                        detailStmt.setInt(3, item.getQuantity());
                        detailStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Gagal menyimpan order", e);
        }
    }
}