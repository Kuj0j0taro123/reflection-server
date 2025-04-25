package ov3rdr1ve.reflection_server.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true, nullable = false)
    private String username;

    @ManyToMany
    @JoinTable(
          name="user_following",
          joinColumns = @JoinColumn(name="user_id"),
          inverseJoinColumns = @JoinColumn(name="following_id")
    )
    private List<User> followingList; // list of users who THIS USER is following

    @ManyToMany(mappedBy = "followingList")
    private List<User> followersList; // list of users that are following THIS USER

    @ManyToMany
    @JoinTable(
            name = "user_liked_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> likedPosts; // posts liked by THIS USER

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts; // posts made by THIS USER

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments; // comments made by THIS USER

    public User() {
    }

    public User(String username, List<User> followingList, List<User> followersList, List<Post> likedPosts, List<Post> posts, List<Comment> comments) {
        this.username = username;
        this.followingList = followingList;
        this.followersList = followersList;
        this.likedPosts = likedPosts;
        this.posts = posts;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<User> followingList) {
        this.followingList = followingList;
    }

    public List<User> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(List<User> followersList) {
        this.followersList = followersList;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
