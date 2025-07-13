package ov3rdr1ve.reflection_server.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.entity.Post;

import java.util.List;

public interface PostService {
    public PostDTO convertToDto(Post post);
    public Post convertToEntity(PostDTO postDTO);

    public List<PostDTO> findAll();
    public PostDTO findById(int id);
    public List<PostDTO> findByAuthorId(int id);
    public List<PostDTO> findByTextContent(String searchTerm, Sort sort);
    public List<PostDTO> findByTextContentOrderByLikesDesc(String searchTerm);
    public List<PostDTO> findTimelineFeed(String username);
    public List<PostDTO> findLikedPosts();
    //public List<PostDTO> findByAuthorUsername(String username);
    public PostDTO createPost(String username, String postText, String mediaUrl);
    public PostDTO likePost(String username, int postId);
    public PostDTO unlikePost(String username, int postId);
    public void dislikePost(String username, int postId);

    // moderator methods below
    public PostDTO removePost(int postId);
    public boolean deletePost(int postId);

}
