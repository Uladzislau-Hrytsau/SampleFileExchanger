package com.exchange.dao;

/**
 * The interface Role dao.
 */
public interface RoleDao {

    /**
     * Add user role integer.
     *
     * @param userId the user id
     * @param roleId the role id
     * @return the integer
     */
    Integer addUserRole(Long userId, Integer roleId);

}
