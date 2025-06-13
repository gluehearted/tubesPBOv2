package db;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePasswords {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_appmakanan";
        String user = "root"; // Replace with your DB username
        String password = "your_password"; // Replace with your DB password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String selectSql = "SELECT id, password FROM users";
            String updateSql = "UPDATE users SET password = ? WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String plainPassword = rs.getString("password");
                String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
                updateStmt.setString(1, hashedPassword);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
                System.out.println("Updated password for user ID: " + id);
            }
            System.out.println("All passwords updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating passwords: " + e.getMessage());
        }
    }
}