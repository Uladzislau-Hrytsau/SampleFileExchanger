package com.exchange.dto.security;

import java.util.Objects;
import java.util.Set;

/**
 * The type User details dto.
 */
public class UserDetailsDto {

    private Long userId;

    private String userName;

    private String userPassword;

    private Set<String> roles;

    /**
     * Instantiates a new User details dto.
     */
    public UserDetailsDto() {
    }

    /**
     * Instantiates a new User details dto.
     *
     * @param userId       the user id
     * @param userName     the user name
     * @param userPassword the user password
     * @param roles        the roles
     */
    public UserDetailsDto(Long userId, String userName, String userPassword, Set<String> roles) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.roles = roles;
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

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets user password.
     *
     * @return the user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets user password.
     *
     * @param userPassword the user password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * Sets roles.
     *
     * @param roles the roles
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsDto that = (UserDetailsDto) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userPassword, that.userPassword) &&
                Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userPassword, roles);
    }

    @Override
    public String toString() {
        return "UserDetailsDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", roles=" + roles +
                '}';
    }
}
