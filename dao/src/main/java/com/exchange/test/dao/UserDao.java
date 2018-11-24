package com.exchange.test.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserDao {

    /**
     * Get all users list.
     *
     * @return all users list
     * @throws DataAccessException
     */
    List<User> getAllUsers() throws DataAccessException;



}
