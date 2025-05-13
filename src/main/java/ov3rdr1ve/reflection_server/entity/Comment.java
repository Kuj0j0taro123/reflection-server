package ov3rdr1ve.reflection_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;


    private String text;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post parentPost;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @CreationTimestamp
    private Instant createdOn;

    public Comment() {
    }

    public Comment(String text, Post parentPost, User author) {
        this.text = text;
        this.parentPost = parentPost;
        this.author = author;
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

    public Post getParentPost() {
        return parentPost;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
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
}
