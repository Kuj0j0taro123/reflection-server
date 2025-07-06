package ov3rdr1ve.reflection_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    private Set<User> userLikes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_views",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> views;


    @OneToMany(mappedBy = "parentPost", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @CreationTimestamp
    private Instant createdOn;

    @OneToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private Media media;

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<User> userLikes) {
        this.userLikes = userLikes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Set<User> getViews() {
        return views;
    }

    public void setViews(Set<User> views) {
        this.views = views;
    }
}
