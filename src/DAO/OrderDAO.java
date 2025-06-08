package DAO;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Order order) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomer().getId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                // Simpan detail item ke order_items
                for (MenuItem item : order.getItems()) {
                    String detailSql = "INSERT INTO order_items (order_id, menu_id, quantity) VALUES (?, ?, ?)";
                    try (PreparedStatement detailStmt = conn.prepareStatement(detailSql)) {
                        detailStmt.setInt(1, orderId);
                        detailStmt.setInt(2, item.getId());
                        detailStmt.setInt(3, item.getQuantity());
                        detailStmt.executeUpdate();
                    }
                }
            }
        }
    }
}

