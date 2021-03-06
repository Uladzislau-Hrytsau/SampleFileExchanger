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
     * Add user long.
     *
     * @param user the user
     * @return the long
     */
    Long addUser(User user);

    /**
     * Update user boolean.
     *
     * @param userUpdatingDto the user updating dto
     * @return the boolean
     */
    Boolean updateUser(UserUpdatingDto userUpdatingDto);

    /**
     * Delete user boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    Boolean deleteUser(Long userId);

    /**
     * Check user by login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    Boolean checkUserByLogin(String login);

    /**
     * Gets user count.
     *
     * @return the user count
     */
    Long getUserCount();
}
