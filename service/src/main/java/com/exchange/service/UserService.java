package com.exchange.service;


import com.exchange.dao.User;

import java.util.List;

/**
 * User Service interface
 * Created by Uladzislau Hrytsau on 1.12.18.
 */
public interface UserService {
    /**
     * Get all users list.
     *
     * @return all users list
     */
    List<User> getAllUsers();

    /**
     * Get user by Id.
     *
     * @param userId user identifier.
     * @return user.
     */
    User getUserByUserId(Long userId);

    /**
     * Get user by login.
     *
     * @param login user login.
     * @return user.
     */
    User getUserByLogin(String login);

    /**
     * Create new user.
     *
     * @param user user.
     * @return new user Id.
     */
    Long addUser(User user);

    /**
     * Update user.
     *
     * @param user user.
     */
    void updateUser(User user);

    /**
     * Delete user.
     *
     * @param userId user identifier.
     */
    void deleteUser(Long userId);

}
