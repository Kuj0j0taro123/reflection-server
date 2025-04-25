package ov3rdr1ve.reflection_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
    private UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public PostDTO convertToDto(Post post) {
        // todo: check here for errors (like, if the post even exists in the database)
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setText(post.getText());
        postDTO.setAuthorId(post.getAuthor().getId());
        postDTO.setUserLikes(post.getUserLikes().size());

        return postDTO;
    }

    @Override
    public Post convertToEntity(PostDTO postDTO) {
        // todo: implement this later
        return null;
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
        User user = userService.findById(id);
        if (user == null)
            return null;
        List<Post> results = postRepository.findByAuthor(user);
        List<PostDTO> posts = new ArrayList<>();

        for (Post result : results){
            PostDTO postDTO = convertToDto(result);
            posts.add(postDTO);
        }

        return posts;
    }

    @Override
    public List<PostDTO> findByAuthorUsername(String username) {
        User user = userService.findByUsername(username);
        if (user == null)
            return null;
        List<Post> results = postRepository.findByAuthor(user);
        List<PostDTO> posts = new ArrayList<>();

        for (Post result : results){
            PostDTO postDTO = convertToDto(result);
            posts.add(postDTO);
        }

        return posts;
    }
}
