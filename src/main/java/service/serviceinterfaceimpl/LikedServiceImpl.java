package src.main.java.service.serviceinterfaceimpl;

import src.main.java.service.serviceinterface.LikedService;

import java.util.List;

public class LikedServiceImpl implements LikedService {
    @Override
    public boolean saveData(Integer userID, boolean likedStatus) {
        return false;
    }

    @Override
    public List<Object> loadData(Integer userID) {
        return null;
    }
}
