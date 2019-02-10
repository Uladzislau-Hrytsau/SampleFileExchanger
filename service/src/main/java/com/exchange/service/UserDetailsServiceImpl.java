package com.exchange.service;

import com.exchange.dao.UserDao;
import com.exchange.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * The type User details service.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    private UserRoleDao userRoleDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao, UserRoleDao userRoleDao) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String password = userDao.getUserPasswordByUserName(login);

        Set<GrantedAuthority> roles = new HashSet<>();

        Set<String> rolesFromTable = new HashSet<>(userRoleDao.getRolesByUserName(login));

        rolesFromTable.forEach((role) -> roles.add(new SimpleGrantedAuthority(role)));

        return new org.springframework.security.core.userdetails.User(login, password, roles);
    }

}
