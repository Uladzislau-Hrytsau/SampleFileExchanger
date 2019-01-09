package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for user.
 * Created by Hrytsau Uladzislau on 4.12.18.
 */
@CrossOrigin
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping("/users")
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Gets user by user id.
     *
     * @param userId the user id
     * @return the user by user id
     */
    @GetMapping(value = "/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByUserId(@PathVariable(value = "id") Long userId) {
        return userService.getUserByUserId(userId);
    }

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     */
    @GetMapping(value = "/user/login/{login}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByLogin(@PathVariable(value = "login") String login) {
        return userService.getUserByLogin(login);
    }

    /**
     * Add user long.
     *
     * @param user the user
     * @return the long
     */
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Update user.
     *
     * @param user the user
     */
    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    /**
     * Delete user.
     *
     * @param userId the user id
     */
    @DeleteMapping(value = "/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        userService.deleteUser(userId);
    }
}
