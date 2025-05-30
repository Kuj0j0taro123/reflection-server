package ov3rdr1ve.reflection_server.dto.actions;

public class FollowUserRequest {
    /*
    1: follow
    0: unfollow
     */
    int action;
    String username;

    public FollowUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
