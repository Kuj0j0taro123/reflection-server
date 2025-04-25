package ov3rdr1ve.reflection_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int id){
        User user = userService.findById(id);

        if (user == null)
            throw new RuntimeException("User " + id + " not not found");

        return user;
    }

    @GetMapping("/users/name/{username}")
    public User getUserByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        return user;
    }
}
