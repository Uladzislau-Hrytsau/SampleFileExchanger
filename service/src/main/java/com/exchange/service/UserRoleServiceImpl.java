package com.exchange.service;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User role service.
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleDao userRoleDao;

    @Value("${userRileService.incorrectUserName}")
    private String incorrectUserName;

    /**
     * Instantiates a new User role service.
     *
     * @param userRoleDao the user role dao
     */
    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Set<String> getRolesByAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<GrantedAuthority> collection = userDetails.getAuthorities();
        Set<String> roles = new HashSet<>(collection.size());
        collection.forEach(item -> roles.add(item.getAuthority()));
        return roles;
    }

}
