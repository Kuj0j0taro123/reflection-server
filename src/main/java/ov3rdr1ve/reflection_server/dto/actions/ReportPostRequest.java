package ov3rdr1ve.reflection_server.dto.actions;

public class ReportPostRequest {
    private int postId;
    private String reason;

    public ReportPostRequest() {
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
