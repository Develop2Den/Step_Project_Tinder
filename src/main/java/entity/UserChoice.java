package src.main.java.entity;

public class UserChoice {

    private int recordId;
    private int userId;
    private int userLikedId;

    public UserChoice(int userId, int userLikedId) {
        this.userId = userId;
        this.userLikedId = userLikedId;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserLikedId() {
        return userLikedId;
    }

    @Override
    public String toString() {
        return "UserChoices{" +
                "userId=" + userId +
                ", userLikedId=" + userLikedId +
                '}';
    }
}
