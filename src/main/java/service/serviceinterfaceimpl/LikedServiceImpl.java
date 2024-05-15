package src.main.java.service.serviceinterfaceimpl;

import src.main.java.dao.daointerface.LikedDAO;
import src.main.java.dao.daointerfaceimpl.CollectionLikedDAO;
import src.main.java.entity.UserChoice;
import src.main.java.service.serviceinterface.LikedService;

import java.util.List;

public class LikedServiceImpl implements LikedService {
    private LikedDAO collectionLikedDAO;

    public LikedServiceImpl() {
        this.collectionLikedDAO = new CollectionLikedDAO();
    }

    @Override
    public boolean saveData(Integer userID, boolean likedStatus) {
        return false;
    }

    @Override
    public List<UserChoice> loadData(Integer userID) {
        List<UserChoice> result = collectionLikedDAO.getAllLikedUsers(userID);
        return result;
    }
}
