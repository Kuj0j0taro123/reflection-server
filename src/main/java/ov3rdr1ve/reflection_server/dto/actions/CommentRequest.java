package ov3rdr1ve.reflection_server.dto.actions;

public class CommentRequest {
    private int id; // post or comment id this comment will go under
    private String text;

    public CommentRequest() {
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
}
