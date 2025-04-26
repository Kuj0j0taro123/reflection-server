package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ov3rdr1ve.reflection_server.dto.CommentDTO;
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

    @GetMapping("/comments/{postId}")
    public ResponseEntity<?> findCommentsByPostId(@PathVariable int postId){
        List<CommentDTO> comments = commentService.findByPostId(postId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
