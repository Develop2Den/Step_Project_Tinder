package DAO.DAOinterface;

import java.sql.SQLException;

public interface MessageDAO {
    void setNewRow () throws SQLException;
    void displayTable() throws SQLException;

}
