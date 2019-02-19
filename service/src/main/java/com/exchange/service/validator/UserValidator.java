package com.exchange.service.validator;

import com.exchange.dao.UserDao;
import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Value("${userService.incorrectLogin}")
    private String incorrectLogin;

    @Value("${userService.incorrectPassword}")
    private String incorrectPassword;

    @Value("${userService.alreadyExist}")
    private String alreadyExist;

    public void validateExistingLogin(String login, UserDao userDao) {
        if (login == null || login.isEmpty()) {
            throw new ValidationException(incorrectLogin);
        }
        if (userDao.checkUserByLogin(login)) {
            throw new ValidationException(alreadyExist);
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ValidationException(incorrectPassword);
        }
    }
}
