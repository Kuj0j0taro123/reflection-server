package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User findById(int id);
    User save(User user);
    void delete(int id);
}
