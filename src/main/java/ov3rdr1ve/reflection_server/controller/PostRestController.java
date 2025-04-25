package ov3rdr1ve.reflection_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public PostDTO getById(@PathVariable int postId){
        return postService.findById(postId);
    }

    @GetMapping("/posts/{authorId}")
    public List<PostDTO> getByAuthorId(@PathVariable int authorId){
        return postService.findByAuthorId(authorId);
    }

    @GetMapping("/posts/name/{authorUsername}")
    public List<PostDTO> getByAuthorUsername(@PathVariable String authorUsername){
        return postService.findByAuthorUsername(authorUsername);
    }
}
