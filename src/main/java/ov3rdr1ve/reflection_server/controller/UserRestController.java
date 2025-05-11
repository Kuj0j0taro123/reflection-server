package ov3rdr1ve.reflection_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
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



    @GetMapping("/user/id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId){
        UserDTO userDTO = userService.findById(userId);

//        if (userDTO == null)
//            throw new RuntimeException("User " + id + " not not found");
        if (userDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        UserDTO userDTO = userService.findByUsername(username);

        if (userDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI(Authentication auth){
        return new ResponseEntity<>(userService.findByUsername(auth.getName()), HttpStatus.OK);

    }
}
