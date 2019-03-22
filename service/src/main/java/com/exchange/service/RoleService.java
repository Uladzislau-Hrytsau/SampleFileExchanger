package com.exchange.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * The interface Role service.
 */
@Service
@Transactional
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
     * @param roleId the role id
     */
    void addUserRole(Long userId, Integer roleId);

}
