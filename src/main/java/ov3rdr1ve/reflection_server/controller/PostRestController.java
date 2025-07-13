package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.dto.actions.LikeRequest;
import ov3rdr1ve.reflection_server.dto.actions.Response;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.service.PostService;
import ov3rdr1ve.reflection_server.service.StorageService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class PostRestController {
    private final PostService postService;
    private final StorageService storageService;


    @Autowired
    public PostRestController(PostService postService, StorageService storageService) {
        this.postService = postService;
        this.storageService = storageService;
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

    @GetMapping("/posts/timeline")
    public List<PostDTO> getTimeline(Authentication auth){
        return postService.findTimelineFeed(auth.getName());
    }

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(
            @RequestPart("text") String text,
            @RequestPart(value = "image", required = false) MultipartFile image
            ){

        // validate post text
        if (text == null || text.trim().isEmpty())
            return new ResponseEntity<>(new Response("Bad Request - Empty post"), HttpStatus.BAD_REQUEST);
        if(text.length() > 300)
            return new ResponseEntity<>(new Response("Bad Request - Post text is too long."), HttpStatus.BAD_REQUEST);

        //todo: try/catch here and return appropriate http code depending on the situation
        String imageUrl = storageService.storeImage(image);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PostDTO createdPost = postService.createPost(username, text, imageUrl);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
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
            PostDTO likedPost = postService.likePost(auth.getName(), req.getId());
            return new ResponseEntity<>(likedPost, HttpStatus.OK);
        }
        if (req.getAction() == 0){ // user unlike
            PostDTO unlikedPost = postService.unlikePost(auth.getName(), req.getId());
            return new ResponseEntity<>(unlikedPost, HttpStatus.OK);
        }
        // todo: add dislike functionality dislike
        return new ResponseEntity<>(new Response("Unknown action"), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/posts/liked")
    public List<PostDTO> getLikedPosts(){
        return postService.findLikedPosts();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId){
        if (postService.deletePost(postId))
            return new ResponseEntity<>(new Response("Post successfully deleted."), HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Bad post deletion request"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/posts")
    public ResponseEntity<?> deleteAllPosts(){
        postService.deleteAllPosts();
        return new ResponseEntity<>(new Response("All posts deleted successfully."), HttpStatus.OK);
    }

//    @GetMapping("/posts/name/{authorUsername}")
//    public List<PostDTO> getByAuthorUsername(@PathVariable String authorUsername){
//        return postService.findByAuthorUsername(authorUsername);
//    }
}
