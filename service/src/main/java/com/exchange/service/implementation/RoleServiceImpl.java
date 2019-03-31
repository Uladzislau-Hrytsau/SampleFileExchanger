package com.exchange.service.implementation;

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
 * The type Role service.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final CommonService commonService;

    @Value("${userRoleService.roleDoesNotAdd}")
    private String roleDoesNotAdd;

    /**
     * Instantiates a new Role service.
     *
     * @param roleDao       the role dao
     * @param commonService the common service
     */
    @Autowired
    public RoleServiceImpl(
            final RoleDao roleDao,
            final CommonService commonService) {
        this.roleDao = roleDao;
        this.commonService = commonService;
    }

    @Override
    public Set<String> getRolesByAuthentication(final Authentication authentication) {
        return this.getRolesByGrantedAuthorities(commonService.getAuthoritiesByAuthentication(authentication));
    }

    @Override
    public void addUserRole(
            final Long userId,
            final Integer roleId) {
        if (roleDao.addUserRole(userId, roleId) == 0) {
            throw new ValidationException(roleDoesNotAdd);
        }
    }

    /**
     * Gets roles by granted authorities.
     *
     * @param grantedAuthorities the granted authorities
     * @return the roles by granted authorities
     */
    public Set<String> getRolesByGrantedAuthorities(final Collection<GrantedAuthority> grantedAuthorities) {
        Set<String> roles = new HashSet<>(grantedAuthorities.size());
        grantedAuthorities.forEach(item -> roles.add(item.getAuthority()));
        return roles;
    }

}
