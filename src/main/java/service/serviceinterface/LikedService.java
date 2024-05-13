package src.main.java.service.serviceinterface;

import java.util.List;

public interface LikedService {

    boolean saveData(Integer userID, boolean likedStatus);
    List<Object> loadData(Integer userID);
}
