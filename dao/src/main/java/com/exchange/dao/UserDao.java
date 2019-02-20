package com.exchange.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao {

    /**
     * Gets all users.
     *
     * @return the all users
     * @throws DataAccessException the data access exception
     */
    List<User> getAllUsers() throws DataAccessException;

    /**
     * Gets user by user id.
     *
     * @param userId the user id
     * @return the user by user id
     * @throws DataAccessException the data access exception
     */
    User getUserByUserId(Long userId) throws DataAccessException;

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     * @throws DataAccessException the data access exception
     */
    User getUserByLogin(String login) throws DataAccessException;

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
     * @throws DataAccessException the data access exception
     */
    Long addUser(User user) throws DataAccessException;

    /**
     * Update user int.
     *
     * @param user the user
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int updateUser(User user) throws DataAccessException;

    /**
     * Delete user int.
     *
     * @param userId the user id
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int deleteUser(Long userId) throws DataAccessException;

    /**
     * Check user by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    boolean checkUserByUserId(Long userId);

    /**
     * Check user by login boolean.
     *
     * @param login the login
     * @return the boolean
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
