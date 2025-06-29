package ov3rdr1ve.reflection_server.dto.actions;

public class ReportCommentRequest {
    private int commentId;
    private String reason;

    public ReportCommentRequest() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
