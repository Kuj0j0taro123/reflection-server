package ov3rdr1ve.reflection_server.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.CommentDTO;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.CommentRepository;
import ov3rdr1ve.reflection_server.repository.PostRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CommentDTO convertToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setAuthorId(comment.getAuthor().getId());
        commentDTO.setParentPostId(comment.getParentPost().getId());
        commentDTO.setAuthorUsername(comment.getAuthor().getUsername());
        commentDTO.setText(comment.getText());
        commentDTO.setCreatedOn(comment.getCreatedOn());

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

    @Override
    @Transactional
    public void comment(int id, String text) throws NoSuchElementException {
        Comment comment = new Comment();
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        Post post = postRepository.findById(id).orElseThrow();

        comment.setText(text);
        comment.setParentPost(post);
        comment.setAuthor(user);

        post.getComments().add(comment);
        user.getComments().add(comment);

        commentRepository.save(comment);
        postRepository.save(post);
        userRepository.save(user);

    }


}
