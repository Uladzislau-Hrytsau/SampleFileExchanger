package com.exchange.config.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * The type User details.
 */
public class UserDetails extends User {

    private Long userId;

    /**
     * Instantiates a new User details.
     *
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     */
    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * Instantiates a new User details.
     *
     * @param username              the username
     * @param password              the password
     * @param enabled               the enabled
     * @param accountNonExpired     the account non expired
     * @param credentialsNonExpired the credentials non expired
     * @param accountNonLocked      the account non locked
     * @param authorities           the authorities
     */
    public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * Instantiates a new User details.
     *
     * @param userId      the user id
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     */
    public UserDetails(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    /**
     * Instantiates a new User details.
     *
     * @param userId                the user id
     * @param username              the username
     * @param password              the password
     * @param enabled               the enabled
     * @param accountNonExpired     the account non expired
     * @param credentialsNonExpired the credentials non expired
     * @param accountNonLocked      the account non locked
     * @param authorities           the authorities
     */
    public UserDetails(Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {

        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
