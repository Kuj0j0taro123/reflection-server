package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.CommentDTO;
import ov3rdr1ve.reflection_server.dto.actions.CommentRequest;
import ov3rdr1ve.reflection_server.dto.actions.Response;
import ov3rdr1ve.reflection_server.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    private CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/post/{postId}")
    public ResponseEntity<?> findCommentsByPostId(@PathVariable int postId){
        List<CommentDTO> comments = commentService.findByPostId(postId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/user/{userId}")
    public List<CommentDTO> findCommentsByUser(@PathVariable int userId){
        return commentService.findByAuthorId(userId);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody CommentRequest req){
        commentService.comment(req.getId(), req.getText());
        return new ResponseEntity<>(new Response("Comment posted successfully"), HttpStatus.OK);
    }
}
