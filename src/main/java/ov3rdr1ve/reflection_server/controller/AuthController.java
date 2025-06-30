package ov3rdr1ve.reflection_server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.actions.LoginRequest;
import ov3rdr1ve.reflection_server.dto.actions.Response;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, UserService userService){
        this.authManager = authManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest creds, HttpServletRequest request){
        //todo: optimize this function later
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));


        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        UserDTO userDTO = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // check if the user is banned
        if (!userDTO.isBanned()){
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout (HttpServletRequest request){
        request.getSession().invalidate();
        return new ResponseEntity<>(new Response("Successfully logged out"), HttpStatus.OK);
    }
}
