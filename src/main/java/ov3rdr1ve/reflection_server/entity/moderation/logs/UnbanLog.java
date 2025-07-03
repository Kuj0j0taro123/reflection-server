package ov3rdr1ve.reflection_server.entity.moderation.logs;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import ov3rdr1ve.reflection_server.entity.User;

import java.time.Instant;

@Entity
@Table(name = "unban_log")
public class UnbanLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "issued_at")
    @CreationTimestamp
    private Instant issuedAt;

    public UnbanLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }
}
