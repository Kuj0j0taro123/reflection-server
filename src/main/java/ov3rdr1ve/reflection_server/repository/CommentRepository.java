package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByParentPost(Post post);
    List<Comment> findByAuthor(User user);
}