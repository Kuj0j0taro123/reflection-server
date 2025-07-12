package ov3rdr1ve.reflection_server.dto;


import java.time.Instant;

public class NotificationDTO {
    private int id;
    private String message;
    private Instant createdOn;

    public NotificationDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
