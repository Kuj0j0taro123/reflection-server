package ov3rdr1ve.reflection_server.entity.report;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;

import java.time.Instant;

@Entity
@Table(name = "reported_post")
public class ReportedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "submitter_id")
    private User submitter;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post reportedPost;

    private String reason;

    @CreationTimestamp
    private Instant createdOn;

    public ReportedPost() {
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

    public Post getReportedPost() {
        return reportedPost;
    }

    public void setReportedPost(Post reportedPost) {
        this.reportedPost = reportedPost;
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

