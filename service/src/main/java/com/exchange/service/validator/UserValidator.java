package com.exchange.service.validator;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type User validator.
 */
@Component
public class UserValidator {

    @Value("${userService.incorrectLoginOrPassword}")
    private String incorrectLoginOrPassword;

    @Value("${userService.alreadyExist}")
    private String alreadyExist;

    /**
     * Validate login and password.
     *
     * @param user    the user
     * @param userDao the user dao
     */
    public void validateLoginAndPassword(User user, UserDao userDao) {
        String login = user.getLogin();
        String password = user.getPassword();
        if (login == null || password == null || login.isEmpty() || password.isEmpty())
            throw new ValidationException(incorrectLoginOrPassword);
        if (userDao.checkUserByLogin(login))
            throw new ValidationException(alreadyExist);
    }

}
