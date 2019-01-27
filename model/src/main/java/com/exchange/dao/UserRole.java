package com.exchange.dao;

/**
 * The type User role.
 */
public class UserRole {

    private Long user_id;

    private Long role_id;

    /**
     * Instantiates a new User role.
     *
     * @param user_id the user id
     * @param role_id the role id
     */
    public UserRole(Long user_id, Long role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * Sets user id.
     *
     * @param user_id the user id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets role id.
     *
     * @return the role id
     */
    public Long getRole_id() {
        return role_id;
    }

    /**
     * Sets role id.
     *
     * @param role_id the role id
     */
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }


}
