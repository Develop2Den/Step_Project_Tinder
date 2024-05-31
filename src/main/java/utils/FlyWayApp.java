package utils;

import java.sql.SQLException;

public class FlyWayApp {


    public static void main(String[] args) throws SQLException {
        DbSetup.migrate(
                "jdbc:postgresql://ep-purple-wildflower-a2bduym5.eu-central-1.aws.neon.tech/neonDB_tinder?sslmode=require",
                "neonDB_tinder_owner",
                "89XinrxabJAI"
        );

    }

}
