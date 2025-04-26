package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ov3rdr1ve.reflection_server.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
