package DAO.DAOinterfaceImpl;

import DAO.DAOinterface.MessageDAO;
import classes.Message;
import classes.UserProfile;
import utils.ConnectionManager;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionMessageDAO implements MessageDAO {

    @Override
    public void saveMsg(int senderId, int receiverId, String content) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, content);
            stmt.executeUpdate();
        }
    }

    @Override
    public  List<Message> getMessages(int userId1, int userId2) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.id, m.sender_id, m.receiver_id, m.content, m.timestamp, u.name AS sender_username " +
                "FROM messages m " +
                "JOIN users u ON m.sender_id = u.id " +
                "WHERE (m.sender_id = ? AND m.receiver_id = ?) OR (m.sender_id = ? AND m.receiver_id = ?) " +
                "ORDER BY m.timestamp";

        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId1);
            stmt.setInt(2, userId2);
            stmt.setInt(3, userId2);
            stmt.setInt(4, userId1);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int senderId = rs.getInt("sender_id");
                    int receiverId = rs.getInt("receiver_id");
                    String content = rs.getString("content");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    String senderUsername = rs.getString("sender_username");
                    messages.add(new Message(id, senderId, receiverId, content, timestamp, senderUsername));
                }
            }
        }
        return messages;
    }

    @Override
    public void deleteMsg(int id) throws SQLException {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public UserProfile getById(int id) throws SQLException {
        String sql = "SELECT id, name, photo_url FROM users WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String photoUrl = rs.getString("photo_url");
                    return new UserProfile(id, name, photoUrl);
                }
            }
        }
        return null;
    }

    public int getCurrentUserIdFromSession(HttpSession session) {
        Object userIdAttribute = session.getAttribute("userId");
        if (userIdAttribute instanceof Integer) {
            return (int) userIdAttribute;
        } else {
            return -1;
        }
    }
}
