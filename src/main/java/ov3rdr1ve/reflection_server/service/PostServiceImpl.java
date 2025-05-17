package ov3rdr1ve.reflection_server.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.PostRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDTO convertToDto(Post post) {
        // todo: check here for errors (like, if the post even exists in the database)
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setText(post.getText());
        postDTO.setAuthorId(post.getAuthor().getId());
        postDTO.setAuthorUsername(post.getAuthor().getUsername());
        postDTO.setUserLikes(post.getUserLikes().size());
        postDTO.setCreatedOn(post.getCreatedOn());

        // check if post is liked by user
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        postDTO.setLiked(post.getUserLikes().contains(user));

        return postDTO;
    }

    @Override
    public Post convertToEntity(PostDTO postDTO) {
        Optional<Post> result = postRepository.findById(postDTO.getId());
        if (result.isPresent())
            return result.get();
        return null;
    }

    @Override
    public List<PostDTO> findAll() {

        List<Post> foundPosts = postRepository.findAll();
        List<PostDTO> postDtos = new ArrayList<>();
        for (Post post : foundPosts){
            postDtos.add(convertToDto(post));
        }
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return postDtos;
    }

    @Override
    public PostDTO findById(int id) {
        Optional<Post> result = postRepository.findById(id);
        if (result.isPresent()){
            return convertToDto(result.get());
        }

        return null;

    }

    @Override
    public List<PostDTO> findByAuthorId(int id) {
        Optional<User> result = userRepository.findById(id);

        if (result.isEmpty())
            return null;

        User user = result.get();

        List<Post> foundPosts = postRepository.findByAuthor(user);
        List<PostDTO> postDtos = new ArrayList<>();

        for (Post post : foundPosts){
            PostDTO postDTO = convertToDto(post);
            postDtos.add(postDTO);
        }

        return postDtos;
    }

    @Override
    public List<PostDTO> findByTextContent(String searchTerm) {
        List<Post> results = postRepository.findByTextContainingIgnoreCase(searchTerm);
        List<PostDTO> ret = new ArrayList<>();

        for (Post post : results){
            ret.add(convertToDto(post));
        }

        return ret;
    }

    @Override
    public void createPost(String username, String postText) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setAuthor(user);
        post.setText(postText);
        post = postRepository.save(post);

        user.getPosts().add(post);
        //user = userRepository.save(user);
    }

    @Override
    @Transactional
    public void likePost(String username, int postId) throws NoSuchElementException {
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        user.getLikedPosts().add(post);
        post.getUserLikes().add(user);

        userRepository.save(user);
        postRepository.save(post);
    }

    @Override
    public void unlikePost(String username, int postId) throws NoSuchElementException {
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        user.getLikedPosts().remove(post);
        post.getUserLikes().remove(user);

        userRepository.save(user);
        postRepository.save(post);
    }

    @Override
    public void dislikePost(String username, int postId) throws NoSuchElementException {

    }

//    @Override
//    public List<PostDTO> findByAuthorUsername(String username) {
//        User user = userService.findByUsername(username);
//        if (user == null)
//            return null;
//        List<Post> results = postRepository.findByAuthor(user);
//        List<PostDTO> posts = new ArrayList<>();
//
//        for (Post result : results){
//            PostDTO postDTO = convertToDto(result);
//            posts.add(postDTO);
//        }
//
//        return posts;
//    }
}
