package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlSelect {

    public static void main(String[] args) throws SQLException {
        record Massages(String text, String inc_msg, String out_msg) { }
        ArrayList<Massages> data = new ArrayList<>();

        try (Connection conn = Conn.mkConn()) {
            String sql = "select text, inc_msg, out_msg from messages";
            PreparedStatement st = conn.prepareStatement(sql);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String text = rs.getString("text");
                    String inc_msg = rs.getString("inc_msg");
                    String out_msg = rs.getString("out_msg");

                    data.add(new Massages(text, inc_msg, out_msg));
                }
            }
        }

        data.forEach(System.out::println);
    }

}
