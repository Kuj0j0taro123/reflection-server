package ov3rdr1ve.reflection_server.service;

import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
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

}