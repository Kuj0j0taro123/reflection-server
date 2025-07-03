package ov3rdr1ve.reflection_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ov3rdr1ve.reflection_server.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
