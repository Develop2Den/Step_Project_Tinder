package src.main.java.service.serviceinterface;

import src.main.java.entity.UserChoices;

import java.util.List;

public interface LikedService {

    boolean saveData(Integer userID, boolean likedStatus);
    List<UserChoices> loadData(Integer userID);
}
