package src.main.java.service.serviceinterfaceimpl;

import src.main.java.dao.daointerface.LikedDAO;
import src.main.java.dao.daointerfaceimpl.CollectionLikedDAO;
import src.main.java.entity.UserChoices;
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
    public List<UserChoices> loadData(Integer userID) {
        List<UserChoices> result = collectionLikedDAO.getAllLikedUsers(userID);
        return result;
    }
}
