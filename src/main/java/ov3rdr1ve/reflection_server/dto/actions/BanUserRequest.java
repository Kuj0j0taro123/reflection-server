package ov3rdr1ve.reflection_server.dto.actions;

public class BanUserRequest {
    private String username;
    private String reason;

    public BanUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
