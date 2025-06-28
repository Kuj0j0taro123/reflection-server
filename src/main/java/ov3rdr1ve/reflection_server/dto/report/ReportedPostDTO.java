package ov3rdr1ve.reflection_server.dto.report;

import java.time.Instant;

public class ReportedPostDTO {
    private int id;
    private int submitterId;
    private int postId;
    private Instant reportedOn;
    private String reason;

    public ReportedPostDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(int submitterId) {
        this.submitterId = submitterId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Instant getReportedOn() {
        return reportedOn;
    }

    public void setReportedOn(Instant reportedOn) {
        this.reportedOn = reportedOn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
