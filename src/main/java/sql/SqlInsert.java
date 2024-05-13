package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlInsert {

    public static void main(String[] args) throws SQLException {
        String sql = "insert into messages (id, text, inc_msg, out_msg) values (?, ?, ?, ?)";

        try (Connection conn = Conn.mkConn()) {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, 2);
            st.setString(2, "Фото супер!");
            st.setString(3, "user2");
            st.setString(4, "user1");
            st.executeUpdate();
        }
    }

}
