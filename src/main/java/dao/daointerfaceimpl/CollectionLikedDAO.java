package src.main.java.dao.daointerfaceimpl;

import src.main.java.dao.daointerface.LikedDAO;
import src.main.java.entity.UserChoice;
import src.main.java.sql.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionLikedDAO implements LikedDAO {

    private List<UserChoice> likedUsers;

    public CollectionLikedDAO() {
        likedUsers = new ArrayList<>();
    }

    @Override
    public List<UserChoice> getAllLikedUsers(int userID) {
        try (Connection conn = Conn.mkConn()) {
            String sql = String.format("select user_id, liked_user from liked where user_id = %s", userID);
            PreparedStatement st = conn.prepareStatement(sql);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int user_id = rs.getInt("user_id");
                    int liked_user = rs.getInt("liked_user");

                    likedUsers.add(new UserChoice(user_id, liked_user));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        likedUsers.forEach(System.out::println);
        return likedUsers;
    }

    @Override
    public void likeUser(Object activeUser, Object selectedUser) {

    }
}
