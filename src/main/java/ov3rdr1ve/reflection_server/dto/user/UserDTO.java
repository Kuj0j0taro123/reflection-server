package ov3rdr1ve.reflection_server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class UserDTO {
    private int id;
    private String username;
    private String description;
    private int numFollowers;
    private int numFollowing;
    private int numPosts;
    private Instant createdOn;
    private boolean isFollowingYou;
    private boolean isFollowedByYou;

    public UserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public int getNumPosts() {
        return numPosts;
    }

    public void setNumPosts(int numPosts) {
        this.numPosts = numPosts;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty(value="isFollowingYou")
    public boolean isFollowingYou() {
        return isFollowingYou;
    }

    public void setFollowingYou(boolean followingYou) {
        isFollowingYou = followingYou;
    }

    @JsonProperty(value="isFollowedByYou")
    public boolean isFollowedByYou() {
        return isFollowedByYou;
    }

    public void setFollowedByYou(boolean followedByYou) {
        isFollowedByYou = followedByYou;
    }
}
