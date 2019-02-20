package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.service.UserRoleService;
import com.exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * The type Security rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityRestController {

    private UserRoleService userRoleService;

    private UserService userService;

    /**
     * Instantiates a new Security rest controller.
     *
     * @param userRoleService the user role service
     * @param userService     the user service
     */
    @Autowired
    public SecurityRestController(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    /**
     * Gets user role.
     *
     * @param httpServletRequest the http servlet request
     * @return the user role
     */
    @GetMapping(value = "/oauth/role")
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getUserRole(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        return userRoleService.getRolesByUserName(principal.getName());
    }

    /**
     * Gets user.
     *
     * @param httpServletRequest the http servlet request
     * @return the user
     */
    @GetMapping(value = "/oauth/user")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUser(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        return userService.getUserByLogin(principal.getName());
    }

}
