package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthor(User author);
    List<Post> findByAuthorOrderByCreatedOnDesc(User author);
    List<Post> findByTextContainingIgnoreCase(String searchTerm, Sort sort);

    @Query("SELECT e FROM Post e " +
            "WHERE LOWER(e.text) LIKE LOWER (CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY SIZE(e.userLikes) DESC")
    List<Post> findByTextOrderByNumLikesDesc(@Param("searchTerm") String searchTerm);
    List<Post> findByAuthorInOrderByCreatedOnDesc(Set<User> authors);

//    @Query("""
//      select p
//      from Post p
//      where p.author in (
//        select f
//        from User u
//        join u.followingList f
//        where u = :user
//      )
//      order by p.createdOn desc
//      """)
//    List<Post> findTimelinePosts(@Param("user") User user);


}
