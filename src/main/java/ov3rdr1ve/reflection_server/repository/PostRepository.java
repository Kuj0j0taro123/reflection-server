package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthor(User author);
    List<Post> findByTextContainingIgnoreCase(String searchTerm, Sort sort);

    @Query("SELECT e FROM Post e " +
            "WHERE LOWER(e.text) LIKE LOWER (CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY SIZE(e.userLikes) DESC")
    List<Post> findByTextOrderByNumLikesDesc(@Param("searchTerm") String searchTerm);

}
