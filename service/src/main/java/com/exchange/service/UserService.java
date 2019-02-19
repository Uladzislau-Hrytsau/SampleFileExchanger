package com.exchange.service;


import com.exchange.dao.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByUserId(Long userId);

    User getUserByLogin(String login);

    String getUserPasswordByUserName(String userName);

    Long addUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId);

}
