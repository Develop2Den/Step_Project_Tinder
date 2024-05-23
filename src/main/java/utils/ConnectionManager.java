package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public final class ConnectionManager {

        private static final String URL = "jdbc:postgresql://localhost:5432/DB_tinder";
        private static final String USER = "user_tinder";
        private static final String PASSWORD = "ut12345";

        private ConnectionManager() { };

        static {
            loadDriver();
        }

        public static Connection open() {
            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        private static void loadDriver() {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

