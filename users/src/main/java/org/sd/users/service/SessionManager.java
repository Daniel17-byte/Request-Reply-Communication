package org.sd.users.service;

import org.sd.users.UsersApplication;
import org.sd.users.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionManager {
    private final Map<String, User> sessions = new HashMap<>();

    public String createSession(User user) {
        String sessionId = generateSessionId();
        sessions.put(sessionId, user);
        return sessionId;
    }

    public boolean isValidSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public User getUsernameForSession(String sessionId) {
        return sessions.get(sessionId);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public User getUserFromSession() {
        if (!isValidSession(UsersApplication.sessionID)){
            return null;
        }

        return sessions.get(UsersApplication.sessionID);

    }
}
