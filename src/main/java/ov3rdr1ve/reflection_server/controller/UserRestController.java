package ov3rdr1ve.reflection_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ov3rdr1ve.reflection_server.dto.NotificationDTO;
import ov3rdr1ve.reflection_server.dto.actions.ChangeProfileDescriptionRequest;
import ov3rdr1ve.reflection_server.dto.actions.FollowUserRequest;
import ov3rdr1ve.reflection_server.dto.actions.Response;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.service.NotificationService;
import ov3rdr1ve.reflection_server.service.StorageService;
import ov3rdr1ve.reflection_server.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;
    private StorageService storageService;
    private NotificationService notificationService;


    public UserRestController(UserService userService,
                              StorageService storageService,
                              NotificationService notificationService){
        this.userService = userService;
        this.storageService = storageService;
        this.notificationService = notificationService;
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
        if (req.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            return new ResponseEntity<>(new Response("You can't follow yourself!"), HttpStatus.BAD_REQUEST);

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
        UserDTO response = null;
        try {
            response = userService.changeUserDescription(req.getDescription());
        }catch (Exception ex){
            return new ResponseEntity<>(new Response("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/user/profile_picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeProfilePicture(@RequestPart(value = "image", required = true) MultipartFile image){
        //todo: try/catch here and return appropriate http code depending on the situation
        String imageUrl = storageService.storeImage(image);
        UserDTO response = userService.changeProfilePicture(imageUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public List<NotificationDTO> getNotifications(){
        //todo: use the notification service you just added as a bean to this controller
        return notificationService.getAllUserNotifications();
    }

    @DeleteMapping("/notification/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable int id){
        notificationService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<?> deleteAllNotifications(){
        notificationService.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
