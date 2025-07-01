package ov3rdr1ve.reflection_server.entity.moderation.reports;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.User;

import java.time.Instant;

@Entity
@Table(name = "reported_comment")
public class ReportedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "submitter_id")
    private User submitter;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment reportedComment;

    private String reason;

    @CreationTimestamp
    private Instant createdOn;

    public ReportedComment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public Comment getReportedComment() {
        return reportedComment;
    }

    public void setReportedComment(Comment reportedComment) {
        this.reportedComment = reportedComment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
