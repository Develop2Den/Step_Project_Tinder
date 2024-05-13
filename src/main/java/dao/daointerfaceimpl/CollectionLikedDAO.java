package src.main.java.dao.daointerfaceimpl;

import src.main.java.dao.daointerface.LikedDAO;

import java.util.List;

public class CollectionLikedDAO implements LikedDAO {


    @Override
    public List<Object> getAllLikedUsers(Integer userID) {
        return null;
    }

    @Override
    public void likeUser(Integer userIDInitiator, Integer userIDSelected) {

    }
}
