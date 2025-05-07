package ov3rdr1ve.reflection_server.dto.actions;

public class CreatePostRequest {
    private String text;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
