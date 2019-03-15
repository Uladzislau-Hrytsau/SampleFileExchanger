package com.exchange.dao;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao {

    /**
     * Gets users by limit and offset.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the users by limit and offset
     */
    List<User> getUsersByLimitAndOffset(Integer limit, Integer offset);

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
     * Update user int.
     *
     * @param user the user
     * @return the int
     */
    int updateUser(User user);

    /**
     * Delete user int.
     *
     * @param userId the user id
     * @return the int
     */
    int deleteUser(Long userId);

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

    /**
     * Gets user count.
     *
     * @return the user count
     */
    Long getUserCount();

}
