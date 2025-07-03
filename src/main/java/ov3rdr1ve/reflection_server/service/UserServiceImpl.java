package ov3rdr1ve.reflection_server.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.PostDTO;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.Notification;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.NotificationRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private NotificationRepository notificationRepository;


    public UserServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setDescription(user.getDescription());
        userDTO.setNumFollowers(user.getFollowersList().size());
        userDTO.setNumFollowing(user.getFollowingList().size());
        userDTO.setNumPosts(user.getPosts().size());
        userDTO.setCreatedOn(user.getCreatedOn());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setBanned(user.isBanned());
        userDTO.setModerator(user.getRoles().contains("MODERATOR"));

        User requester = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        userDTO.setFollowedByYou(user.getFollowersList().contains(requester));
        userDTO.setFollowingYou(user.getFollowingList().contains(requester));

        return userDTO;
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        Optional<User> result = userRepository.findById(userDTO.getId());
        if (result.isPresent())
            return result.get();

        return null;
    }

    @Override
    public UserDTO findByUsername(String username) {

        Optional<User> result = userRepository.findByUsername(username);
        if (result.isPresent()){
            User user = result.get();
            UserDTO userDTO = convertToDto(user);
            return userDTO;
        }

        return null;
    }

    @Override
    public UserDTO findById(int id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()){
            User user = result.get();
            UserDTO userDTO = convertToDto(user);
            return userDTO;
        }

        return null;
    }

    @Override
    public List<UserDTO> findUsersByUsername(String username) {
        List<User> results = userRepository.findByUsernameContainingIgnoreCase(username);
        List<UserDTO> ret = new ArrayList<>();

        for (User user : results){
            ret.add(convertToDto(user));
        }

        return ret;
    }



    @Override
    @Transactional
    public UserDTO followUser(String userRequesting, String userReceiving) throws NoSuchElementException {
        User requester = userRepository.findByUsername(userRequesting).orElseThrow();
        User receiver = userRepository.findByUsername(userReceiving).orElseThrow();

        requester.getFollowingList().add(receiver);
        receiver.getFollowersList().add(requester);



        Notification notification = new Notification();
        notification.setReceiver(receiver);
        notification.setSender(requester);
        notification.setMessage(requester.getUsername() + " followed you.");
        receiver.getReceivedNotifications().add(notification);

        notificationRepository.save(notification);
        userRepository.saveAll(List.of(requester, receiver));

        return convertToDto(receiver);

    }

    @Override
    @Transactional
    public UserDTO unfollowUser(String userRequesting, String userReceiving) throws NoSuchElementException {
        User requester = userRepository.findByUsername(userRequesting).orElseThrow();
        User receiver = userRepository.findByUsername(userReceiving).orElseThrow();

        requester.getFollowingList().remove(receiver);
        receiver.getFollowersList().remove(requester);

        userRepository.saveAll(List.of(requester, receiver));

        return convertToDto(receiver);
    }

    @Override
    @Transactional
    public UserDTO changeUserDescription(String description) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        user.setDescription(description);
        userRepository.save(user);
        return convertToDto(user);
    }

    @Override
    @Transactional
    public UserDTO changeProfilePicture(String imageUrl) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        user.setProfilePicture(imageUrl);
        userRepository.save(user);

        return convertToDto(user);
    }
}