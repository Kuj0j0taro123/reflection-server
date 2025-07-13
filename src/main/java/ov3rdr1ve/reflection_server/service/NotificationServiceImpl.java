package ov3rdr1ve.reflection_server.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.NotificationDTO;
import ov3rdr1ve.reflection_server.entity.Notification;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.NotificationRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService{

    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationDTO convertToDto(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setCreatedOn(notification.getCreatedOn());
        return notificationDTO;
    }

    @Override
    public Notification convertToEntity(NotificationDTO notificationDTO) {
        try{
            return notificationRepository.findById(notificationDTO.getId()).orElseThrow();
        }catch (NoSuchElementException elementException){
            return null;
        }
    }

    @Override
    public Notification findById(int id) {
        try{
            return notificationRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException elementException){
            return null;
        }
    }

    @Override
    public NotificationDTO createNotification() {
        return null;
    }


    @Override
    public List<NotificationDTO> getAllUserNotifications(){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        ArrayList<NotificationDTO> notifications = new ArrayList<>();
        for (Notification result : user.getReceivedNotifications()){
            notifications.add(convertToDto(result));
        }

        return notifications;
    }

    @Override
    public boolean deleteById(int id) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        Set<Notification> notifications = user.getReceivedNotifications();
        boolean ret = notifications.removeIf(n -> n.getId() == id);
        userRepository.save(user);
        return ret;
    }

    @Override
    public void deleteAll() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        user.getReceivedNotifications().clear();
        userRepository.save(user);
    }


}
