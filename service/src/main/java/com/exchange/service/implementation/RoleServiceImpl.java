package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.RoleDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Value("${userRoleService.roleDoesNotAdd}")
    private String roleDoesNotAdd;

    /**
     * Instantiates a new User role service.
     *
     * @param roleDao the user role dao
     */
    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<String> getRolesByAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<GrantedAuthority> collection = userDetails.getAuthorities();
        Set<String> roles = new HashSet<>(collection.size());
        collection.forEach(item -> roles.add(item.getAuthority()));
        return roles;
    }

    @Override
    public void addUserRole(Long userId, Integer roleId) {
        if (roleDao.addUserRole(userId, roleId) == 0) {
            throw new ValidationException(roleDoesNotAdd);
        }
    }

}
