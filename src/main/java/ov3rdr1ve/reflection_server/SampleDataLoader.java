package ov3rdr1ve.reflection_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.CommentRepository;
import ov3rdr1ve.reflection_server.repository.PostRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @Autowired
    public SampleDataLoader(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Existing sample data
        User alice = new User();
        alice.setUsername("alice01");
        userRepository.save(alice);

        User bob = new User();
        bob.setUsername("bob");
        userRepository.save(bob);

        Post post1 = new Post();
        post1.setText("Hello, world!");
        post1.setAuthor(alice);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setText("sample text");
        post2.setAuthor(bob);
        postRepository.save(post2);

        Comment comment1 = new Comment();
        comment1.setText("Nice post!");
        comment1.setAuthor(bob);
        comment1.setParentPost(post1);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setText("Thanks!");
        comment2.setAuthor(alice);
        comment2.setParentPost(post2);
        commentRepository.save(comment2);

        // New sample data
        User charlie = new User();
        charlie.setUsername("charlie");
        userRepository.save(charlie);

        User david = new User();
        david.setUsername("david");
        userRepository.save(david);

        Post post3 = new Post();
        post3.setText("This is another sample post.");
        post3.setAuthor(charlie);
        postRepository.save(post3);

        Post post4 = new Post();
        post4.setText("This is yet another sample post.");
        post4.setAuthor(david);
        postRepository.save(post4);

        Comment comment3 = new Comment();
        comment3.setText("Great post!");
        comment3.setAuthor(alice);
        comment3.setParentPost(post3);
        commentRepository.save(comment3);

        Comment comment4 = new Comment();
        comment4.setText("Awesome!");
        comment4.setAuthor(bob);
        comment4.setParentPost(post4);
        commentRepository.save(comment4);

        Comment comment5 = new Comment();
        comment5.setText("Interesting!");
        comment5.setAuthor(charlie);
        comment5.setParentPost(post1);
        commentRepository.save(comment5);

        Comment comment6 = new Comment();
        comment6.setText("Well done!");
        comment6.setAuthor(david);
        comment6.setParentPost(post2);
        commentRepository.save(comment6);

        System.out.println("Sample data loaded");

    }
}
