package ov3rdr1ve.reflection_server.entity.report;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import ov3rdr1ve.reflection_server.entity.User;

import java.time.Instant;

@Entity
@Table(name = "reported_user")
public class ReportedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "submitter_id")
    private User submitter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reportedUser;

    private String reason;

    @CreationTimestamp
    private Instant createdOn;

    public ReportedUser(){

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

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
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
