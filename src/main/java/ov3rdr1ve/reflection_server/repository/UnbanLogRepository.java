package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ov3rdr1ve.reflection_server.entity.moderation.logs.UnbanLog;

public interface UnbanLogRepository extends JpaRepository<UnbanLog, Integer> {
}
