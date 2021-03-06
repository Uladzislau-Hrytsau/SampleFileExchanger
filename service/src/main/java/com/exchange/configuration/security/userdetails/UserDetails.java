package com.exchange.configuration.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

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
    public UserDetails(
            final String username,
            final String password,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * Instantiates a new User details.
     *
     * @param userId      the user id
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     */
    public UserDetails(
            final Long userId,
            final String username,
            final String password,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
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
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                '}';
    }
}
