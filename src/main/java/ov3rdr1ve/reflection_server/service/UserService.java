package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public UserDTO convertToDto(User user);
    public User convertToEntity(UserDTO userDTO);


    public UserDTO findByUsername(String username);
    public UserDTO findById(int id);
    public List<UserDTO> findUsersByUsername(String username);

}
