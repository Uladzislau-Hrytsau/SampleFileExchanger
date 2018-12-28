package com.exchange.service;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private UserDao userDao;

    @Autowired
    private UserValidator userValidator;

    @Value("${userService.deleteError}")
    private String deleteError;
    @Value("${userService.updateError}")
    private String updateError;
    @Value("${userService.incorrectLogin}")
    private String incorrectLogin;
    @Value("${userService.incorrectId}")
    private String incorrectId;
    @Value("${userService.userDoesNotExist}")
    private String userDoesNotExist;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByUserId(Long userId) {
        if (userId == null || userId < 0L)
            throw new ValidationException(incorrectId);
        if (!userDao.checkUserByUserId(userId))
            throw new ValidationException(userDoesNotExist);
        return userDao.getUserByUserId(userId);
    }

    @Override
    public User getUserByLogin(String login) {
        if (login == null || login.isEmpty())
            throw new ValidationException(incorrectLogin);
        if (!userDao.checkUserByLogin(login))
            throw new ValidationException(userDoesNotExist);
        return userDao.getUserByLogin(login);
    }

    @Override
    public Long addUser(User user) {
        userValidator.validateLoginAndPassword(user, userDao);
        return userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userValidator.validateLoginAndPassword(user, userDao);
        if (userDao.updateUser(user) == 0)
            throw new InternalServerException(updateError);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userId == null || userId < 0L)
            throw new ValidationException(incorrectId);
        if (userDao.deleteUser(userId) == 0)
            throw new InternalServerException(deleteError);
    }

}
