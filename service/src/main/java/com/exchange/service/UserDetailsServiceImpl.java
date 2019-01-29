package com.exchange.service;

import com.exchange.dao.User;
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
import java.util.stream.Stream;

/**
 * The type User details service.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.getUserByLogin(login);

        Set<GrantedAuthority> roles = new HashSet<>();

        Long userId = userDao.getUserIdByLogin(login);
        Set<String> rolesFromTable = userRoleDao.getRolesByUserId(userId);

        Stream stream = rolesFromTable.stream();
        stream.forEach((role) -> roles.add(new SimpleGrantedAuthority(role.toString())));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }

}
