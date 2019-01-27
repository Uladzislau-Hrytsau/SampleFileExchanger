package com.exchange.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * UserDao interface.
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public interface UserDao {

    /**
     * Get all users list.
     *
     * @return all users list
     * @throws DataAccessException the data access exception
     */
    List<User> getAllUsers() throws DataAccessException;

    /**
     * Get user by Id.
     *
     * @param userId user identifier.
     * @return user. user by user id
     * @throws DataAccessException the data access exception
     */
    User getUserByUserId(Long userId) throws DataAccessException;

    /**
     * Get user by login.
     *
     * @param login user login.
     * @return user. user by login
     * @throws DataAccessException the data access exception
     */
    User getUserByLogin(String login) throws DataAccessException;

    /**
     * Create new user.
     *
     * @param user user.
     * @return new user Id.
     * @throws DataAccessException the data access exception
     */
    Long addUser(User user) throws DataAccessException;

    /**
     * Update user.
     *
     * @param user user.
     * @return new user Id.
     * @throws DataAccessException the data access exception
     */
    int updateUser(User user) throws DataAccessException;

    /**
     * Delete user.
     *
     * @param userId user identifier.
     * @return int int
     * @throws DataAccessException the data access exception
     */
    int deleteUser(Long userId) throws DataAccessException;

    /**
     * Check user by user identifier.
     *
     * @param userId the user id
     * @return boolean boolean
     */
    boolean checkUserByUserId(Long userId);

    /**
     * Check user by user login.
     *
     * @param login the login
     * @return boolean boolean
     */
    boolean checkUserByLogin(String login);

    /**
     * Gets user id by login.
     *
     * @param login the login
     * @return the user id by login
     */
    Long getUserIdByLogin(String login);

}
