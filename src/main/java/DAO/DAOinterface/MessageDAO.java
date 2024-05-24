package DAO.DAOinterface;

import classes.Message;
import classes.UserProfile;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
    void saveMsg (int senderId, int receiverId, String content) throws SQLException;
    void deleteMsg (int id) throws SQLException;
    List <Message> getMessages(int userId1, int userId2) throws SQLException ;
    int getCurrentUserIdFromSession(HttpSession session) throws SQLException;
    UserProfile getById(int id) throws SQLException;

}
