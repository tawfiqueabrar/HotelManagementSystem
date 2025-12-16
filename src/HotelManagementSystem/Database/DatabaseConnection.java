package HotelManagementSystem.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/HotelManagementSystem" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "RafiIkra69";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }
}