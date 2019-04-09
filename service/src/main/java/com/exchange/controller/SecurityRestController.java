package com.exchange.controller;

import com.exchange.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * The type Security rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityRestController {

    private final RoleService roleService;

    /**
     * Instantiates a new Security rest controller.
     *
     * @param roleService the role service
     */
    @Autowired
    public SecurityRestController(final RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Gets user role.
     *
     * @param authentication the authentication
     * @return the user role
     */
    @GetMapping(value = "/oauth/role")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<String> getUserRole(final Authentication authentication) {
        return roleService.getRolesByAuthentication(authentication);
    }

}
