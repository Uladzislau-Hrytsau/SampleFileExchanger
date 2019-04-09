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
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    /**
     * Instantiates a new User rest controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserRestController(final UserService userService) {
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
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Response getUsersByPageAndSize(
            @RequestParam(value = "page", required = false, defaultValue = "null") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "null") final Integer size) {
        return userService.getUsersAndCountByPageAndSize(page, size);
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@RequestBody final User user) {
        userService.addUser(user);
    }

    /**
     * Update user.
     *
     * @param userUpdatingDto the user updating dto
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(@RequestBody final UserUpdatingDto userUpdatingDto) {
        userService.updateUser(userUpdatingDto);
    }

    /**
     * Delete user.
     *
     * @param id the id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@RequestParam(value = "id") final Long id) {
        userService.deleteUser(id);
    }

}
