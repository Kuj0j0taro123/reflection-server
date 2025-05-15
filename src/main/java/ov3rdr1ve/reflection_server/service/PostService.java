package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.entity.Post;

import java.util.List;

public interface PostService {
    public PostDTO convertToDto(Post post);
    public Post convertToEntity(PostDTO postDTO);

    public List<PostDTO> findAll();
    public PostDTO findById(int id);
    public List<PostDTO> findByAuthorId(int id);
    public List<PostDTO> findByTextContent(String searchTerm);
    //public List<PostDTO> findByAuthorUsername(String username);
    public void createPost(String username, String postText);
}
