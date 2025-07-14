package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.CommentDTO;
import ov3rdr1ve.reflection_server.dto.actions.CommentRequest;
import ov3rdr1ve.reflection_server.dto.actions.LikeRequest;
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
        CommentDTO createdComment = commentService.createComment(req.getId(), req.getText());
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PostMapping("/comment/like")
    public ResponseEntity<?> likeComment(@RequestBody LikeRequest req, Authentication auth){
        if (req.getAction() == 1){ // user like
            CommentDTO likedComment = commentService.likeComment(auth.getName(), req.getId());
            return new ResponseEntity<>(likedComment, HttpStatus.OK);
        }
        if (req.getAction() == 0){ // user unlike
            CommentDTO unlikedComment = commentService.unlikeComment(auth.getName(), req.getId());

            return new ResponseEntity<>(unlikedComment, HttpStatus.OK);
        }
        // todo: add dislike functionality dislike
        return new ResponseEntity<>(new Response("Unknown action"), HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId){
        if (commentService.deleteComment(commentId))
            return new ResponseEntity<>(new Response("Comment successfully deleted."), HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Bad comment deletion request"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/comments")
    public ResponseEntity<?> deleteAllComments(){
        commentService.deleteAllComments();
        return new ResponseEntity<>(new Response("All comments deleted successfully."), HttpStatus.OK);
    }
}
