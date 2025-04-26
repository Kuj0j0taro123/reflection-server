package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {
    private PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getById(@PathVariable int postId){
        PostDTO result = postService.findById(postId);
        if (result == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/posts/{authorId}")
    public ResponseEntity<?> getByAuthorId(@PathVariable int authorId){
        List<PostDTO> results = postService.findByAuthorId(authorId);

        if (results == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

//    @GetMapping("/posts/name/{authorUsername}")
//    public List<PostDTO> getByAuthorUsername(@PathVariable String authorUsername){
//        return postService.findByAuthorUsername(authorUsername);
//    }
}
