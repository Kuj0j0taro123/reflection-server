package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.CommentDTO;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;

import java.util.List;

public interface CommentService {
    public CommentDTO convertToDto(Comment comment);
    public Comment convertToEntity(CommentDTO commentDTO);

    public List<CommentDTO> findByPostId(int postId);
//    public CommentDTO findByAuthorId(int authorId);
//    public CommentDTO findByAuthorUsername(String username);
}
