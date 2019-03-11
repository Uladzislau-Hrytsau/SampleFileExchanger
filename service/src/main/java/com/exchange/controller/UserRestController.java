package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserRestController {

    private final UserService userService;

    /**
     * Instantiates a new User rest controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets all users.
     *
     * @param page the page
     * @param size the size
     * @return the all users
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/{page}/{size}")//, params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUsers(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return userService.getAllUsers(page, size);
    }

    /**
     * Gets users amount.
     *
     * @return the users amount
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/amount")
    @ResponseStatus(value = HttpStatus.OK)
    public Integer getUsersAmount() {
        return userService.getUsersAmount();
    }

    /**
     * Gets user by user id.
     *
     * @param userId the user id
     * @return the user by user id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/user/login", params = {"login"})
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByLogin(@RequestParam("login") String login) {
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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/user", params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        userService.deleteUser(userId);
    }

}
