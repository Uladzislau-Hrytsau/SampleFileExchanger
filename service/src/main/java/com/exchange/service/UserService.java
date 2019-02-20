package com.exchange.service;


import com.exchange.dao.User;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets all users.
     *
     * @return the all users
     */
    List<User> getAllUsers();

    /**
     * Gets user by user id.
     *
     * @param userId the user id
     * @return the user by user id
     */
    User getUserByUserId(Long userId);

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     */
    User getUserByLogin(String login);

    /**
     * Gets user password by user name.
     *
     * @param userName the user name
     * @return the user password by user name
     */
    String getUserPasswordByUserName(String userName);

    /**
     * Add user long.
     *
     * @param user the user
     * @return the long
     */
    Long addUser(User user);

    /**
     * Update user.
     *
     * @param user the user
     */
    void updateUser(User user);

    /**
     * Delete user.
     *
     * @param userId the user id
     */
    void deleteUser(Long userId);

}
