package classes;

import java.sql.Timestamp;
import java.util.Objects;

public class Liked {
    private int id;
    private int userId;
    private int likedUserId;
    private Timestamp timestamp;

    public Liked(int id, int userId, int likedUserId, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.likedUserId = likedUserId;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getLikedUserId() {
        return likedUserId;
    }

    public Timestamp getCreated_at() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Liked liked = (Liked) o;
        return id == liked.id && userId == liked.userId && likedUserId == liked.likedUserId && Objects.equals(timestamp, liked.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, likedUserId, timestamp);
    }

    @Override
    public String toString() {
        return "Liked{" +
                "id=" + id +
                ", userId=" + userId +
                ", likedUserId=" + likedUserId +
                ", timestamp=" + timestamp +
                '}';
    }
}
