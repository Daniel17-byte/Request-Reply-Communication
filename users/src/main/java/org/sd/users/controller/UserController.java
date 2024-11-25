package org.sd.users.controller;

import org.sd.users.model.LoginCredentials;
import org.sd.users.model.User;
import org.sd.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://gateway:8080"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserByUUID(@PathVariable UUID uuid) {
        User user = userService.getUserByUUID(uuid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userResponse =  userService.createUser(user);

        if (userResponse != null) {
            userService.authenticate(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userResponse =  userService.updateUser(user);

        if (userResponse != null) {
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID uuid) {
        User user = userService.getUserByUUID(uuid);
        userService.deleteDevices(user.getUsername());
        userService.deleteUser(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getLoggedInUser")
    public ResponseEntity<User> getUser() {
        User user = userService.getUserFromSession();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody LoginCredentials credentials) {
        if(userService.authenticate(credentials.getUsername(), credentials.getPassword())){
            User user = userService.getUserFromSession();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
