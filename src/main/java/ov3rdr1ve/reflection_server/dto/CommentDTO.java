package ov3rdr1ve.reflection_server.dto;

import ov3rdr1ve.reflection_server.dto.user.UserDTO;

public class CommentDTO {
    private int id;
    private String text;
    private int parentPostId;
    private int authorId;

    public CommentDTO() {
    }

    public CommentDTO(int id, String text, int parentPostId, int authorId) {
        this.id = id;
        this.text = text;
        this.parentPostId = parentPostId;
        this.authorId = authorId;
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
}
