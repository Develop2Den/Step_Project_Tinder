package services;

import DAO.DAOinterface.MessageDAO;
import classes.Message;
import classes.UserProfile;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class MessagesService implements MessageDAO {
    private final MessageDAO messageDAO;

    public MessagesService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public void saveMsg(int senderId, int receiverId, String content) throws SQLException {
        messageDAO.saveMsg(senderId, receiverId, content);
    }

    @Override
    public void deleteMsg(int id) throws SQLException {
        messageDAO.deleteMsg(id);
    }

    @Override
    public List<Message> getMessages(int userId1, int userId2) throws SQLException {
        return messageDAO.getMessages(userId1, userId2);
    }

    @Override
    public int getCurrentUserIdFromSession(HttpSession session) throws SQLException {
        return messageDAO.getCurrentUserIdFromSession(session);
    }

    @Override
    public UserProfile getById(int id) throws SQLException {
        return getById(id);
    }
}
