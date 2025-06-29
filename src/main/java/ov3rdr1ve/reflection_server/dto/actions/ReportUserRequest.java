package ov3rdr1ve.reflection_server.dto.actions;

public class ReportUserRequest {
    private int userId;
    private String reason;

    public ReportUserRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
