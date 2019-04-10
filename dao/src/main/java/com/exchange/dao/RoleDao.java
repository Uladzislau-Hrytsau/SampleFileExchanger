package com.exchange.dao;

/**
 * The interface Role dao.
 */
public interface RoleDao {

    /**
     * Add user role boolean.
     *
     * @param userId the user id
     * @param roleId the role id
     * @return the boolean
     */
    Boolean addUserRole(Long userId, Integer roleId);
}
