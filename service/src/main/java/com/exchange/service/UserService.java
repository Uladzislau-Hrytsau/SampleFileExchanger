package com.exchange.service;


import com.exchange.dao.User;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.wrapper.Response;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets users and count by page and size.
     *
     * @param page the page
     * @param size the size
     * @return the users and count by page and size
     */
    Response getUsersAndCountByPageAndSize(Integer page, Integer size);

    /**
     * Add user.
     *
     * @param user the user
     */
    void addUser(User user);

    /**
     * Update user.
     *
     * @param userUpdatingDto the user updating dto
     */
    void updateUser(UserUpdatingDto userUpdatingDto);

    /**
     * Delete user.
     *
     * @param userId the user id
     */
    void deleteUser(Long userId);

    /**
     * Gets user count.
     *
     * @return the user count
     */
    Long getUserCount();

}
