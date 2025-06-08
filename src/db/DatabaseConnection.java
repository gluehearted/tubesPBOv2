/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Prakosa A
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_appmakanan"; // ganti nama database
    private static final String USER = "root"; // ganti user jika berbeda
    private static final String PASSWORD = ""; // isi password mysql kamu

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
