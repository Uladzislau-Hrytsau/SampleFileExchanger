package com.exchange.dao;

import com.exchange.dto.user.UserUpdatingDto;

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
     * @param userUpdatingDto the user updating dto
     * @return the int
     */
    Integer updateUser(UserUpdatingDto userUpdatingDto);

    /**
     * Delete user int.
     *
     * @param userId the user id
     * @return the int
     */
    Integer deleteUser(Long userId);

    /**
     * Check user by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    Boolean checkUserByUserId(Long userId);

    /**
     * Check user by login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    Boolean checkUserByLogin(String login);

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
