package src.main.java.dao.daointerface;

import java.util.List;

public interface LikedDAO {

    List<Object> getAllLikedUsers(Integer userID);
    void likeUser(Integer userIDInitiator, Integer userIDSelected);
}
