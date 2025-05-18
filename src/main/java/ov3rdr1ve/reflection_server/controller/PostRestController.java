package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.dto.actions.CreatePostRequest;
import ov3rdr1ve.reflection_server.dto.actions.LikeRequest;
import ov3rdr1ve.reflection_server.dto.actions.Response;
import ov3rdr1ve.reflection_server.service.PostService;

import java.security.Principal;
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

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts(){
        return postService.findAll();
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest createPostRequest, Authentication auth){
        if (createPostRequest.getText().isEmpty())
            return new ResponseEntity<>(new Response("Empty post text."), HttpStatus.BAD_REQUEST);
        if (createPostRequest.getText().length() > 300)
            return new ResponseEntity<>(new Response("Post text is too long."), HttpStatus.BAD_REQUEST);

        String username = auth.getName();
        postService.createPost(username, createPostRequest.getText());

        return new ResponseEntity<>(new Response("Post created successfully"), HttpStatus.CREATED);

    }

    @GetMapping("/posts/search")
    public ResponseEntity<?> searchPosts(@RequestParam String q, @RequestParam String s){ // q as in query (for posts), s as in sort
        // List<PostDTO> results = postService.findByTextContent(q);
        List<PostDTO> results;
        if (s.equalsIgnoreCase("top"))
            results = postService.findByTextContentOrderByLikesDesc(q);
        else if (s.equalsIgnoreCase("recent")){
            results = postService.findByTextContent(q, Sort.by(Sort.Direction.DESC, "createdOn"));
        }
        else{
            results = null;
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/post/like")
    public ResponseEntity<?> likePost(@RequestBody LikeRequest req, Authentication auth){
        if (req.getAction() == 1){ // user like
            postService.likePost(auth.getName(), req.getId());
            return new ResponseEntity<>(new Response("Post liked successfully"), HttpStatus.OK);
        }
        if (req.getAction() == 0){ // user unlike
            postService.unlikePost(auth.getName(), req.getId());
            return new ResponseEntity<>(new Response("Post unliked successfully"), HttpStatus.OK);
        }
        // todo: add dislike functionality dislike
        return new ResponseEntity<>(new Response("Unknown action"), HttpStatus.BAD_REQUEST);

    }

//    @GetMapping("/posts/name/{authorUsername}")
//    public List<PostDTO> getByAuthorUsername(@PathVariable String authorUsername){
//        return postService.findByAuthorUsername(authorUsername);
//    }
}
