package sql;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {

    @SneakyThrows
    static Connection mkConn() {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/tinderDB",
                "postgres",
                "12345"
        );
    }

}
