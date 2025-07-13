package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.NotificationDTO;
import ov3rdr1ve.reflection_server.entity.Notification;

import java.util.List;

public interface NotificationService {
    public NotificationDTO convertToDto(Notification notification);
    public Notification convertToEntity(NotificationDTO notificationDTO);

    public Notification findById(int id);
    public NotificationDTO createNotification();
    public List<NotificationDTO> getAllUserNotifications();

    public boolean deleteById(int id);
    public void deleteAll();
}
