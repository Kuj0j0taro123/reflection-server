package ov3rdr1ve.reflection_server.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void followUser(String userRequesting, String userReceiving) throws NoSuchElementException {
        User requester = userRepository.findByUsername(userRequesting).orElseThrow();
        User receiver = userRepository.findByUsername(userReceiving).orElseThrow();

        requester.getFollowingList().add(receiver);
        receiver.getFollowersList().add(requester);

        userRepository.saveAll(List.of(requester, receiver));

    }

    @Override
    @Transactional
    public void unfollowUser(String userRequesting, String userReceiving) throws NoSuchElementException {
        User requester = userRepository.findByUsername(userRequesting).orElseThrow();
        User receiver = userRepository.findByUsername(userReceiving).orElseThrow();

        requester.getFollowingList().remove(receiver);
        receiver.getFollowersList().remove(requester);

        userRepository.saveAll(List.of(requester, receiver));
    }
}