package sql;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {

    @SneakyThrows
    public static Connection mkConn() {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/DB_tinder",
                "user_tinder",
                "ut12345"
        );
    }

}
