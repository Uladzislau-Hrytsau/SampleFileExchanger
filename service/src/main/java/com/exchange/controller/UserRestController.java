package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.service.UserService;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * Gets users by page and size.
     *
     * @param page the page
     * @param size the size
     * @return the users by page and size
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users", params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Response getUsersByPageAndSize(
            @RequestParam(value = "page", required = false, defaultValue = "null") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "null") Integer size) {
        return userService.getUsersAndCountByPageAndSize(page, size);
    }

    /**
     * Gets users amount.
     *
     * @return the users amount
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/users/amount")
    @ResponseStatus(value = HttpStatus.OK)
    public Long getUsersAmount() {
        return userService.getUserCount();
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
    @PostMapping("/users")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    /**
     * Update user.
     *
     * @param userUpdatingDto the user updating dto
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/users")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(@RequestBody UserUpdatingDto userUpdatingDto) {
        userService.updateUser(userUpdatingDto);
    }

    /**
     * Delete user.
     *
     * @param id the id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/users", params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
    }

}
