package com.exchange.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * The interface User role service.
 */
@Service
@Transactional
public interface UserRoleService {

    /**
     * Gets roles by authentication.
     *
     * @param authentication the authentication
     * @return the roles by authentication
     */
    Set<String> getRolesByAuthentication(Authentication authentication);

}
