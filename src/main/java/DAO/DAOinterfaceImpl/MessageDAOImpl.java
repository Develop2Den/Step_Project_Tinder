package DAO.DAOinterfaceImpl;

import DAO.DAOinterface.DAO;
import classes.Message;
import utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements DAO<Message> {

    @Override
    public List<Message> getAll() throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.id, m.sender_id, m.receiver_id, m.content, m.timestamp, u.name AS sender_username " +
                "FROM messages m " +
                "JOIN users u ON m.sender_id = u.id " +
                "ORDER BY m.timestamp";
        try (Connection conn = ConnectionManager.open();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        return messages;
    }

    @Override
    public Message getById(int id) throws SQLException {
        String sql = "SELECT m.id, m.sender_id, m.receiver_id, m.content, m.timestamp, u.name AS sender_username " +
                "FROM messages m " +
                "JOIN users u ON m.sender_id = u.id " +
                "WHERE m.id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int senderId = rs.getInt("sender_id");
                    int receiverId = rs.getInt("receiver_id");
                    String content = rs.getString("content");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    String senderUsername = rs.getString("sender_username");
                    return new Message(id, senderId, receiverId, content, timestamp, senderUsername);
                }
            }
        }
        return null;
    }

    @Override
    public void save(Message message) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getReceiverId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Message message) throws SQLException {
        String sql = "UPDATE messages SET sender_id = ?, receiver_id = ?, content = ?, timestamp = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getReceiverId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, message.getTimestamp());
            stmt.setInt(5, message.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Message> getMessagesBetweenUsers(int userId1, int userId2) throws SQLException {
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

    public void saveMessage(int senderId, int receiverId, String content) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, content);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }
}
