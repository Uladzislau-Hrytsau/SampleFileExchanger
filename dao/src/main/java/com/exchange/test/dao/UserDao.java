package com.exchange.test.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * UserDao interface.
 */
public interface UserDao {

    /**
     * Get all users list.
     *
     * @return all users list
     */
    List<User> getAllUsers() throws DataAccessException;

    /**
     * Get user by Id.
     *
     * @param userId user identifier.
     * @return user.
     */
    User getUserByUserId(Long userId) throws DataAccessException;

    /**
     * Get user by login.
     *
     * @param login user login.
     * @return user.
     * @throws DataAccessException
     */
    User getUserByLogin(String login) throws DataAccessException;

    /**
     * Create new user.
     *
     * @param user user.
     * @return new user Id.
     */
    Long addUser(User user) throws DataAccessException;

    /**
     * Update user.
     *
     * @param user user.
     * @return new user Id.
     */
    int updateUser(User user) throws DataAccessException;

    /**
     * Delete user.
     *
     * @param userId user identifier.
     * @return
     * @throws DataAccessException
     */
    int deleteUser(Long userId) throws DataAccessException;

}
