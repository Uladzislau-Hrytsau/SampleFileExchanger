package com.exchange.service;

import org.springframework.security.core.Authentication;

import java.util.Set;

/**
 * The interface Role service.
 */
public interface RoleService {

    /**
     * Gets roles by authentication.
     *
     * @param authentication the authentication
     * @return the roles by authentication
     */
    Set<String> getRolesByAuthentication(Authentication authentication);

    /**
     * Add user role.
     *
     * @param userId the user id
     * @param roleId the role idz
     */
    void addUserRole(Long userId, Integer roleId);
}
