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

import java.util.*;

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
        commentDTO.setAuthorProfilePicture(comment.getAuthor().getProfilePicture());
        commentDTO.setRemovedByModerator(comment.isRemovedByModerator());

        if (comment.getUserLikes() != null) {
            commentDTO.setUserLikes(comment.getUserLikes().size());
            // check if comment is liked by user
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
            commentDTO.setLiked(comment.getUserLikes().contains(user));
        }
        else{
            commentDTO.setUserLikes(0);
            commentDTO.setLiked(false);
        }

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
    public List<CommentDTO> findByAuthorId(int authorId) {
        User author = userRepository.findById(authorId).orElseThrow();
        List<Comment> results = commentRepository.findByAuthorOrderByCreatedOnDesc(author);
        ArrayList<CommentDTO> comments = new ArrayList<>();

        for (Comment result : results){
            comments.add(convertToDto(result));
        }
        return comments;
    }

    @Override
    @Transactional
    public CommentDTO createComment(int id, String text) throws NoSuchElementException {
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

        return convertToDto(comment);

    }

    @Override
    @Transactional
    public CommentDTO likeComment(String username, int commentId) throws NoSuchElementException{
        User user = userRepository.findByUsername(username).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        user.getLikedComments().add(comment);
        comment.getUserLikes().add(user);

        userRepository.save(user);
        commentRepository.save(comment);

        return convertToDto(comment);

    }

    @Override
    public CommentDTO unlikeComment(String username, int commentId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        user.getLikedComments().remove(comment);
        comment.getUserLikes().remove(user);

        userRepository.save(user);
        commentRepository.save(comment);

        return convertToDto(comment);
    }

    @Override
    public void dislikeComment(String username, int commentId) {

    }

    @Override
    public boolean deleteComment(int commentId) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        boolean ret = user.getComments().removeIf(n -> n.getId() == commentId);
        userRepository.save(user);
        return ret;
    }

    @Override
    @Transactional
    public void deleteAllComments() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        user.getComments().clear();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public CommentDTO removeComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User moderator = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        comment.setText("Comment removed by " + moderator.getUsername() + ".");
        comment.setRemovedByModerator(true);
        commentRepository.save(comment);

        return convertToDto(comment);
    }


}
