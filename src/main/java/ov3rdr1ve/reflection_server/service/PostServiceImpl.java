package ov3rdr1ve.reflection_server.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.entity.Media;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.MediaRepository;
import ov3rdr1ve.reflection_server.repository.PostRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.*;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, MediaRepository mediaRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public PostDTO convertToDto(Post post) {
        // todo: check here for errors (like, if the post even exists in the database)
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setText(post.getText());
        postDTO.setAuthorId(post.getAuthor().getId());
        postDTO.setAuthorUsername(post.getAuthor().getUsername());

        if (post.getUserLikes() != null){
            postDTO.setUserLikes(post.getUserLikes().size());
            // check if post is liked by user
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
            postDTO.setLiked(post.getUserLikes().contains(user));
        }
        else{
            postDTO.setUserLikes(0);
            postDTO.setLiked(false);
        }

        postDTO.setCreatedOn(post.getCreatedOn());

        if (post.getComments() != null)
            postDTO.setNumComments(post.getComments().size());
        else
            postDTO.setNumComments(0);

        if (post.getMedia() != null)
            postDTO.setMediaUrl(post.getMedia().getUrl());

        if (post.getViews() != null){
            postDTO.setViews(post.getViews().size());
        }

        postDTO.setAuthorProfilePicture(post.getAuthor().getProfilePicture());



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
            // this will cause problems if the app does not enforce users logging in
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
            Post post = result.get();
            // views is a set, so this should be fine
            post.getViews().add(user);
            return convertToDto(post);
        }

        return null;

    }

    @Override
    public List<PostDTO> findByAuthorId(int id) {
        Optional<User> result = userRepository.findById(id);

        if (result.isEmpty())
            return null;

        User user = result.get();

        List<Post> foundPosts = postRepository.findByAuthorOrderByCreatedOnDesc(user);
        List<PostDTO> postDtos = new ArrayList<>();

        for (Post post : foundPosts){
            PostDTO postDTO = convertToDto(post);
            postDtos.add(postDTO);
        }

        return postDtos;
    }

    @Override
    public List<PostDTO> findByTextContent(String searchTerm, Sort sort) {
        List<Post> results = postRepository.findByTextContainingIgnoreCase(searchTerm, sort);
        List<PostDTO> ret = new ArrayList<>();

        for (Post post : results){
            ret.add(convertToDto(post));
        }

        return ret;
    }

    @Override
    public List<PostDTO> findByTextContentOrderByLikesDesc(String searchTerm) {
        List<Post> results = postRepository.findByTextOrderByNumLikesDesc(searchTerm);
        List<PostDTO> ret = new ArrayList<>();

        for (Post post : results){
            ret.add(convertToDto(post));
        }
        return ret;
    }

    @Override
    public List<PostDTO> findTimelineFeed(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Set<User> following = user.getFollowingList();

        List<PostDTO> feed = new ArrayList<>();

        List<Post> results = postRepository.findByAuthorInOrderByCreatedOnDesc(following);

        for (Post post : results){
            feed.add(convertToDto(post));
        }
        return feed;
    }

    @Override
    public List<PostDTO> findLikedPosts() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        Set<Post> results = user.getLikedPosts();

        ArrayList<PostDTO> posts = new ArrayList<>();

        for (Post post : results){
            posts.add(convertToDto(post));
        }

        return posts;
    }

    @Override
    @Transactional
    public PostDTO createPost(String username, String postText, String mediaUrl) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        Media media = null;

        post.setAuthor(user);
        post.setText(postText);


        if (mediaUrl != null && !mediaUrl.trim().isEmpty()){
            media = new Media();
            media.setPost(post);
            media.setUrl(mediaUrl);
            mediaRepository.save(media);
        }

        post.setMedia(media);

        post = postRepository.save(post);

        user.getPosts().add(post);

        return convertToDto(post);
    }

    @Override
    @Transactional
    public PostDTO likePost(String username, int postId) throws NoSuchElementException {
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        user.getLikedPosts().add(post);
        post.getUserLikes().add(user);

        userRepository.save(user);
        postRepository.save(post);

        return convertToDto(post);
    }

    @Override
    public PostDTO unlikePost(String username, int postId) throws NoSuchElementException {
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        user.getLikedPosts().remove(post);
        post.getUserLikes().remove(user);

        userRepository.save(user);
        postRepository.save(post);

        return convertToDto(post);
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
