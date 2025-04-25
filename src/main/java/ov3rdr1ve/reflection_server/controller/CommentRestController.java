package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.repository.CommentRepository;
import ov3rdr1ve.reflection_server.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    // using this for now. Will add a service later
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentRestController(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @GetMapping("/comments/{postId}")
    public List<Comment> findCommentsByPostId(@PathVariable int postId){
        // later, do this in the service

        // no error checking, add later
        Post parentPost = postRepository.findById(postId).get();
        List<Comment> postComments = commentRepository.findByParentPost(parentPost);

        return postComments;
    }
}
