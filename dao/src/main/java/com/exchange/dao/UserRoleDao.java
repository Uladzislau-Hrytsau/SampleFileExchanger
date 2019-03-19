package com.exchange.dao;

/**
 * The interface User role dao.
 */
public interface UserRoleDao {

    /**
     * Add user role.
     *
     * @param userId the user id
     * @param roleId the role id
     * @return the integer
     */
    Integer addUserRole(Long userId, Integer roleId);

}
