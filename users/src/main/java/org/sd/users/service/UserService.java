package org.sd.users.service;

import org.sd.users.UsersApplication;
import org.sd.users.model.User;
import org.sd.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PBKDF2PasswordEncoderHelper passwordEncoderHelper;
    private final SessionManager sessionManager;
    private static final String BASE_URL = "http://devices:8080/api/devices";
    private final RestTemplate restTemplate;

    public UserService(UserRepository userRepository, PBKDF2PasswordEncoderHelper passwordEncoderHelper, SessionManager sessionManager, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoderHelper = passwordEncoderHelper;
        this.sessionManager = sessionManager;
        this.restTemplate = restTemplate;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoderHelper.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {
        user.setPassword(passwordEncoderHelper.encode(user.getPassword()));
        userRepository.updateUserByUuid(user.getUuid(), user);
        return user;
    }

    public User getUserByUUID(UUID uuid) {
        if (userRepository.existsUserByUuid(uuid)) {
            return userRepository.findByUuid(uuid);
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID uuid) {
        User user = getUserByUUID(uuid);
        userRepository.delete(user);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            return false;
        }

        if (passwordEncoderHelper.matches(password, user.getPassword())) {
            UsersApplication.sessionID = sessionManager.createSession(user);
            return true;
        } else
            return false;

    }

    public User getUserFromSession() {
        return sessionManager.getUserFromSession();
    }

    public void deleteDevices(String username) {
        URI uri = URI.create(BASE_URL + "/delete/" + username);

        try {
            restTemplate.delete(uri);
            System.out.println("Devices deleted successfully for user: " + username);
        } catch (Exception e) {
            System.err.println("Failed to delete devices for user " + username + ": " + e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
