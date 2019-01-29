package com.exchange.dao;

import java.util.Set;

/**
 * The interface User role dao.
 */
public interface UserRoleDao {

    /**
     * Gets roles by user id.
     *
     * @param userId the user id
     * @return the roles by user id
     */
    Set<String> getRolesByUserId(Long userId);


}
