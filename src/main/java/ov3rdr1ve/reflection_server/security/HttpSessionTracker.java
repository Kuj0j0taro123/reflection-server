package ov3rdr1ve.reflection_server.security;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class HttpSessionTracker
        implements ApplicationListener<AuthenticationSuccessEvent>,
        HttpSessionListener {

    // username -> list of active HttpSession objects
    private final ConcurrentMap<String, CopyOnWriteArrayList<HttpSession>> sessions = new ConcurrentHashMap<>();

    // on successful auth, grab the session and store it
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // only care about web logins
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpSession session = attrs.getRequest().getSession(false);
            if (session != null) {
                String username = event.getAuthentication().getName();
                sessions
                        .computeIfAbsent(username, u -> new CopyOnWriteArrayList<>())
                        .add(session);
            }
        }
    }

    // when a session is destroyed (timeout/logout), remove it
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sessions.forEach((username, list) -> {
            list.remove(session);
            if (list.isEmpty()) {
                sessions.remove(username);
            }
        });
    }

    /** Helper for others to fetch all active sessions for a user */
    public List<HttpSession> getSessions(String username) {
        return sessions.getOrDefault(username, new CopyOnWriteArrayList<>());
    }
}
