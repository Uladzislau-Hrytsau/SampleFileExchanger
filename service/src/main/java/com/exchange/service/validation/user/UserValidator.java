package com.exchange.service.validation.user;

import com.exchange.dao.UserDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.validation.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type User validator.
 */
@Component
public class UserValidator {

    private final CommonValidator commonValidator;

    @Value("${userService.incorrectLogin}")
    private String incorrectLogin;
    @Value("${userService.incorrectPassword}")
    private String incorrectPassword;
    @Value("${userService.incorrectInformation}")
    private String incorrectInformation;
    @Value("${userService.alreadyExist}")
    private String alreadyExist;
    @Value("${userService.incorrectId}")
    private String incorrectId;

    /**
     * Instantiates a new User validator.
     *
     * @param commonValidator the common validator
     */
    @Autowired
    public UserValidator(CommonValidator commonValidator) {
        this.commonValidator = commonValidator;
    }

    /**
     * Validate user id.
     *
     * @param userId the user id
     */
    public void validateUserId(Long userId) {
        if (!commonValidator.isValidIdentifier(userId)) {
            throw new ValidationException(incorrectId);
        }
    }

    /**
     * Validate existing login.
     *
     * @param login   the login
     * @param userDao the user dao
     */
    public void validateExistingLogin(String login, UserDao userDao) {
        if (!commonValidator.isValidString(login)) {
            throw new ValidationException(incorrectLogin);
        }
        if (userDao.checkUserByLogin(login)) {
            throw new ValidationException(alreadyExist);
        }
    }

    /**
     * Validate password.
     *
     * @param password the password
     */
    public void validatePassword(String password) {
        if (!commonValidator.isValidString(password)) {
            throw new ValidationException(incorrectPassword);
        }
    }

    /**
     * Validate information.
     *
     * @param information the information
     */
    public void validateInformation(String information) {
        if (!commonValidator.isValidString(information)) {
            throw new ValidationException(incorrectInformation);
        }
    }
}
