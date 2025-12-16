package HotelManagementSystem.Database;

import java.sql.*;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/HotelManagementSystem?useSSL=false&serverTimezone=UTC";
    private static final String USER = " ";
    private static final String PASS = " ";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new SQLException("MySQL Driver missing");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}