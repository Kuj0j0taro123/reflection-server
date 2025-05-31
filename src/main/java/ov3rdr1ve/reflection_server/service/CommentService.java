package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.CommentDTO;
import ov3rdr1ve.reflection_server.entity.Comment;

import java.util.List;

public interface CommentService {
    public CommentDTO convertToDto(Comment comment);
    public Comment convertToEntity(CommentDTO commentDTO);

    public List<CommentDTO> findByPostId(int postId);
    public List<CommentDTO> findByAuthorId(int authorId);
    public void createComment(int id, String text);
    public void likeComment(String username, int commentId);
    public void unlikeComment(String username, int commentId);
    public void dislikeComment(String username, int commentId);
    
//    public CommentDTO findByAuthorId(int authorId);
//    public CommentDTO findByAuthorUsername(String username);
}
