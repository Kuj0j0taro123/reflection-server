package ov3rdr1ve.reflection_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;

import java.time.Instant;

public class CommentDTO {
    private int id;
    private String text;
    private int parentPostId;
    private int authorId;
    private String authorUsername;
    private Instant createdOn;
    private boolean isLiked;
    private int userLikes;
    private String authorProfilePicture;
    private boolean removedByModerator;

    public CommentDTO() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(int parentPostId) {
        this.parentPostId = parentPostId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty(value="isLiked")
    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(int userLikes) {
        this.userLikes = userLikes;
    }

    public String getAuthorProfilePicture() {
        return authorProfilePicture;
    }

    public void setAuthorProfilePicture(String authorProfilePicture) {
        this.authorProfilePicture = authorProfilePicture;
    }

    public boolean isRemovedByModerator() {
        return removedByModerator;
    }

    public void setRemovedByModerator(boolean removedByModerator) {
        this.removedByModerator = removedByModerator;
    }
}
