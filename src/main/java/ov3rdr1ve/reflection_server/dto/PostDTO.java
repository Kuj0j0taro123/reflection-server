package ov3rdr1ve.reflection_server.dto;


import java.time.Instant;

public class PostDTO {
    private int id;
    private String authorUsername;
    private String text;
    private int userLikes;
    private int authorId;
    private Instant createdOn;

    public PostDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(int userLikes) {
        this.userLikes = userLikes;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
