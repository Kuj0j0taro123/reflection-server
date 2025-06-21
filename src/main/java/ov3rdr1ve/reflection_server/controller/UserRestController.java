package ov3rdr1ve.reflection_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.actions.ChangeProfileDescriptionRequest;
import ov3rdr1ve.reflection_server.dto.actions.FollowUserRequest;
import ov3rdr1ve.reflection_server.dto.actions.Response;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.service.UserService;

import java.util.NoSuchElementException;

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

    @GetMapping("/users/search")
    public ResponseEntity<?> getUsersByUsername(@RequestParam String q){
        return new ResponseEntity<>(userService.findUsersByUsername(q), HttpStatus.OK);

    }

    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@RequestBody FollowUserRequest req, Authentication auth){

        UserDTO userGettingFollowed = null;

        try{
            if(req.getAction() == 1) // follow request
                userGettingFollowed = userService.followUser(auth.getName(), req.getUsername());
            else if(req.getAction() == 0) // unfollow request
                userGettingFollowed = userService.unfollowUser(auth.getName(), req.getUsername());
            else
                return new ResponseEntity<>(new Response("Unknown action"), HttpStatus.BAD_REQUEST);
        } catch(NoSuchElementException ex){
            return new ResponseEntity<>(new Response("User not found"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userGettingFollowed, HttpStatus.OK);
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI(Authentication auth){
        return new ResponseEntity<>(userService.findByUsername(auth.getName()), HttpStatus.OK);

    }

    @PostMapping("/user/description")
    public ResponseEntity<?> changeAccountDescription(@RequestBody ChangeProfileDescriptionRequest req){
        UserDTO res = null;
        try {
            res = userService.changeUserDescription(req.getDescription());
        }catch (Exception ex){
            return new ResponseEntity<>(new Response("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
