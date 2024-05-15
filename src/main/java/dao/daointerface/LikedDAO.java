package src.main.java.dao.daointerface;

import src.main.java.entity.UserChoices;

import java.util.List;

public interface LikedDAO {

    List<UserChoices> getAllLikedUsers(int userID);
    void likeUser(Object activeUser, Object selectedUser);
}
