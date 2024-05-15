package src.main.java.dao.daointerface;

import src.main.java.entity.UserChoice;

import java.util.List;

public interface LikedDAO {

    List<UserChoice> getAllLikedUsers(int userID);
    void likeUser(Object activeUser, Object selectedUser);
}
