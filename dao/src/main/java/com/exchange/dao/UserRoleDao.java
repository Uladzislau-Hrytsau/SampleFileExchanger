package com.exchange.dao;

import java.util.List;

/**
 * The interface User role dao.
 */
public interface UserRoleDao {

    /**
     * Gets roles by user name.
     *
     * @param userName the user name
     * @return the roles by user name
     */
    List<String> getRolesByUserName(String userName);

}
