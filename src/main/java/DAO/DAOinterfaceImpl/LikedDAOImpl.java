package DAO.DAOinterfaceImpl;

import DAO.DAOinterface.DAO;
import classes.Liked;
import classes.User;
import utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikedDAOImpl implements DAO<Liked> {

    @Override
    public List<Liked> getAll() throws SQLException {
        List<Liked> likes = new ArrayList<>();
        String sql = "SELECT id, user_id1, user_id2, type, timestamp FROM likes";
        try (Connection conn = ConnectionManager.open();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId1 = rs.getInt("user_id1");
                int userId2 = rs.getInt("user_id2");
                String type = rs.getString("type");
                Timestamp timestamp = rs.getTimestamp("timestamp");
                likes.add(new Liked(id, userId1, userId2, type, timestamp));
            }
        }
        return likes;
    }

    @Override
    public Liked getById(int id) throws SQLException {
        String sql = "SELECT id, user_id1, user_id2, type, timestamp FROM likes WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId1 = rs.getInt("user_id1");
                    int userId2 = rs.getInt("user_id2");
                    String type = rs.getString("type");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    return new Liked(id, userId1, userId2, type, timestamp);
                }
            }
        }
        return null;
    }

    @Override
    public void save(Liked liked) throws SQLException {
        String sql = "INSERT INTO likes (user_id1, user_id2, type, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, liked.getUserId());
            stmt.setInt(2, liked.getLikedUserId());
            stmt.setString(3, liked.getType());
            stmt.setTimestamp(4, liked.getTimestamp());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Liked liked) throws SQLException {
        String sql = "UPDATE likes SET user_id1 = ?, user_id2 = ?, type = ?, timestamp = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, liked.getUserId());
            stmt.setInt(2, liked.getLikedUserId());
            stmt.setString(3, liked.getType());
            stmt.setTimestamp(4, liked.getTimestamp());
            stmt.setInt(5, liked.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM likes WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void likeProfile(int userId1, int userId2) throws SQLException {
        saveOrUpdateProfile(userId1, userId2, "like");
    }

    public void dislikeProfile(int userId1, int userId2) throws SQLException {
        saveOrUpdateProfile(userId1, userId2, "dislike");
    }

    private void saveOrUpdateProfile(int userId1, int userId2, String type) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM likes WHERE user_id1 = ? AND user_id2 = ?";
        String insertSql = "INSERT INTO likes (user_id1, user_id2, type, timestamp) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE likes SET type = ?, timestamp = ? WHERE user_id1 = ? AND user_id2 = ?";

        try (Connection conn = ConnectionManager.open();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId1);
            checkStmt.setInt(2, userId2);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, type);
                        updateStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        updateStmt.setInt(3, userId1);
                        updateStmt.setInt(4, userId2);
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId1);
                        insertStmt.setInt(2, userId2);
                        insertStmt.setString(3, type);
                        insertStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public List<User> getLikedProfiles(int userId) throws SQLException {
        return getProfilesByType(userId, "like");
    }

    public List<User> getDislikedProfiles(int userId) throws SQLException {
        return getProfilesByType(userId, "dislike");
    }

    private List<User> getProfilesByType(int userId, String type) throws SQLException {
        List<User> profiles = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.photo_url " +
                "FROM users u " +
                "JOIN likes l ON u.id = l.user_id2 " +
                "WHERE l.user_id1 = ? AND l.type = ?";

        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String photoUrl = rs.getString("photo_url");
                    profiles.add(new User(id, name, photoUrl));
                }
            }
        }
        return profiles;
    }
}
