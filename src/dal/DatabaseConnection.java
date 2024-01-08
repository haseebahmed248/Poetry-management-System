package dal;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance; // Singleton instance

    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;

    // Private constructor to prevent external instantiation
    private DatabaseConnection() {
        // Load properties and initialize database connection details
        Properties p = new Properties();
        try {
            p.load(new FileReader("config//dal.properties"));
            DB_URL = p.getProperty("DB_URL");
            DB_USER = p.getProperty("DB_USER");
            DB_PASSWORD = p.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static method to get the singleton instance
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Method to get a database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
