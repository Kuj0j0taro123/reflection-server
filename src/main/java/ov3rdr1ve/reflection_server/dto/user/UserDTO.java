package ov3rdr1ve.reflection_server.dto.user;

public class UserDTO {
    private int id;
    private String username;
    private String description;
    private int numFollowers;
    private int numFollowing;
    private int numPosts;

    public UserDTO() {
    }

    public UserDTO(int id, String username, String description, int numFollowers, int numFollowing, int numPosts) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
        this.numPosts = numPosts;
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
}
