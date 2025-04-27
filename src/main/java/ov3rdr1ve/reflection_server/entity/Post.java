package ov3rdr1ve.reflection_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authorUsername;

    private String text;

    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    private List<User> userLikes;

    @OneToMany(mappedBy = "parentPost", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    public Post() {
    }

    public Post(String text, List<User> userLikes, User author) {
        this.authorUsername = author.getUsername();
        this.text = text;

        this.userLikes = userLikes;
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

    public List<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<User> userLikes) {
        this.userLikes = userLikes;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        this.authorUsername = author.getUsername();
    }
}
