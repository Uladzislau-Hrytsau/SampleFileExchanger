package com.exchange.test.service.rest;

import com.exchange.test.dao.User;
import com.exchange.test.dao.UserDao;
import com.exchange.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Service implementation.
 * Created by Uladzislau Hrytsau on 1.12.18.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() throws DataAccessException {
        return userDao.getAllUsers();
    }

    public User getUserByUserId(Long userId) throws DataAccessException {
        return userDao.getUserByUserId(userId);
    }

    public User getUserByLogin(String login) throws DataAccessException {
        return userDao.getUserByLogin(login);
    }

    public Long addUser(User user) throws DataAccessException {
        return userDao.addUser(user);
    }

    public int updateUser(User user) throws DataAccessException {
        return userDao.updateUser(user);
    }

    public int deleteUser(Long userId) throws DataAccessException {
        return userDao.deleteUser(userId);
    }
}
