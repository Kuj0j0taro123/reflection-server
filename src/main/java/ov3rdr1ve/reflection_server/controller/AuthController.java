package ov3rdr1ve.reflection_server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.actions.LoginRequest;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager){
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest creds, HttpServletRequest request){
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    @GetMapping("/whoami")
    public String whoami(Authentication auth){
        StringBuilder sb = new StringBuilder();
        sb.append("You are logged in as ");
        sb.append(auth.getName());
        sb.append("\n");
        sb.append("You have the following authorities: ");
        sb.append(auth.getAuthorities());
        sb.append("\n");
        sb.append("You have the credentials: ");
        sb.append(auth.getCredentials());
        sb.append("\n");
        sb.append("Here are some details: ");
        sb.append(auth.getDetails());
        sb.append("\n");
        sb.append("Are you authenticated? ");
        sb.append(auth.isAuthenticated());

        return sb.toString();
    }
}
