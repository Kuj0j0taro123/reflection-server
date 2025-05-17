package ov3rdr1ve.reflection_server.dto.actions;

// request to like a post or comment
public class LikeRequest {
    private int id; // id of post or comment
    /*
    * 1: like
    * 0: unlike
    * -1: dislike
    * */
    private int action;

    public LikeRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
