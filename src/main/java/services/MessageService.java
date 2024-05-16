package services;

import DAO.DAOinterface.MessageDAO;

import java.sql.SQLException;

public class MessageService implements MessageDAO {
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public void setNewRow() throws SQLException {
        messageDAO.setNewRow();
    }

    @Override
    public void displayTable() throws SQLException {
        messageDAO.displayTable();
    }
}
