package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ov3rdr1ve.reflection_server.entity.Media;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
