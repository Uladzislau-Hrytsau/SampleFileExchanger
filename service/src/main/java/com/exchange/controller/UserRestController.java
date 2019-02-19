package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByUserId(@PathVariable(value = "id") Long userId) {
        return userService.getUserByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/user/login/{login}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByLogin(@PathVariable(value = "login") String login) {
        return userService.getUserByLogin(login);
    }

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        userService.deleteUser(userId);
    }

}
