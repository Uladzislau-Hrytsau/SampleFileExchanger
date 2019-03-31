package com.exchange.service.implementation;

import com.exchange.dao.UserDetailsDao;
import com.exchange.dto.security.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserDetailsDao userDetailsDao;

    /**
     * Instantiates a new User details service.
     *
     * @param userDetailsDao the user details dao
     */
    @Autowired
    public UserDetailsServiceImpl(final UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        UserDetailsDto userDetailsDto = userDetailsDao.getUserDetailsByLogin(login);
        return new com.exchange.config.security.userdetails.UserDetails(
                userDetailsDto.getUserId(), userDetailsDto.getUserName(), userDetailsDto.getUserPassword(), this.getGrantedAuthoritiesByUserDetailsDto(userDetailsDto));
    }

    /**
     * Gets granted authorities by user details dto.
     *
     * @param userDetailsDto the user details dto
     * @return the granted authorities by user details dto
     */
    public Set<GrantedAuthority> getGrantedAuthoritiesByUserDetailsDto(final UserDetailsDto userDetailsDto) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userDetailsDto.getRoles().forEach(item -> roles.add(new SimpleGrantedAuthority(item)));
        return roles;
    }

}
