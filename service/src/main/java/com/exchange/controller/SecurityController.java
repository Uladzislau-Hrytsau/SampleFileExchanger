package com.exchange.controller;

import com.exchange.service.UserRoleService;
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
 * The type Security controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityController {

    private UserRoleService userRoleService;

    /**
     * Instantiates a new Security controller.
     *
     * @param userRoleService the user role service
     */
    @Autowired
    public SecurityController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * Gets user role.
     *
     * @param httpServletRequest the http servlet request
     * @return the user role
     */
    @GetMapping(value = "/oauth/user")
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getUserRole(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        return userRoleService.getRolesByUserName(principal.getName());
    }

}
