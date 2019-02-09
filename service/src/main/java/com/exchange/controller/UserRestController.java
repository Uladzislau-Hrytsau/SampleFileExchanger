package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Rest controller for user.
 * Created by Hrytsau Uladzislau on 4.12.18.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Gets all users.
     *
     * @param request the request
     * @return the all users
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUsers(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return userService.getAllUsers();
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
    @DeleteMapping(value = "/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * Current user name string.
     *
     * @param request the request
     * @return the string
     */
    @GetMapping("/username")
    public String currentUserName(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
}
