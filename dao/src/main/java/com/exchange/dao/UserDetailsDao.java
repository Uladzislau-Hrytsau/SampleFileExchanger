package com.exchange.dao;

import com.exchange.dto.UserDetailsDto;

/**
 * The interface User details dao.
 */
public interface UserDetailsDao {

    /**
     * Gets user details by login.
     *
     * @param login the login
     * @return the user details by login
     */
    UserDetailsDto getUserDetailsByLogin(String login);

}
