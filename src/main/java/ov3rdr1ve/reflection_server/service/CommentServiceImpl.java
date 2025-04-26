package ov3rdr1ve.reflection_server.service;

import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.CommentDTO;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.repository.CommentRepository;
import ov3rdr1ve.reflection_server.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Override
    public CommentDTO convertToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setAuthorId(comment.getAuthor().getId());
        commentDTO.setParentPostId(comment.getParentPost().getId());
        commentDTO.setText(comment.getText());

        return commentDTO;
    }

    @Override
    public Comment convertToEntity(CommentDTO commentDTO) {
        Optional<Comment> result = commentRepository.findById(commentDTO.getId());
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public List<CommentDTO> findByPostId(int postId) {
        Optional<Post> postResult = postRepository.findById(postId);
        if(postResult.isEmpty())
            return null;

        Post post = postResult.get();

        List<Comment> commentsResult = commentRepository.findByParentPost(post);
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (Comment comment : commentsResult){
            CommentDTO commentDTO = convertToDto(comment);
            commentDTOList.add(commentDTO);
        }

        return commentDTOList;
    }


}
