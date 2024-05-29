package DAO.DAOinterfaceImpl;

import DAO.DAOinterface.DAO;
import classes.User;
import utils.ConnectionManager;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements DAO<User> {

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, photo_url FROM users";

        try (Connection conn = ConnectionManager.open();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String photoUrl = rs.getString("photo_url");
                users.add(new User(id, name, photoUrl));
            }
        }
        return users;
    }

    @Override
    public User getById(int id) throws SQLException {
        String sql = "SELECT id, name, photo_url FROM users WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String photoUrl = rs.getString("photo_url");
                    return new User(id, name, photoUrl);
                }
            }
        }
        return null;
    }

    @Override
    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users (name, photo_url) VALUES (?, ?)";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhoto());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, photo_url = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhoto());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = ConnectionManager.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public int getUserIdByUsername(String username) throws SQLException {
        int userId = -1;
        try (Connection connection = ConnectionManager.open()) {
            String sql = "SELECT id FROM users WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("id");
                    }
                }
            }
        }
        return userId;
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