package src.main.java.service.serviceinterface;

import src.main.java.entity.UserChoice;

import java.util.List;

public interface LikedService {

    boolean saveData(Integer userID, boolean likedStatus);
    List<UserChoice> loadData(Integer userID);
}
