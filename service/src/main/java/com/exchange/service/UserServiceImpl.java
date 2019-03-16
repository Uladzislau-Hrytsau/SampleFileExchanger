package com.exchange.service;

import com.exchange.dao.Pagination;
import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.validator.UserValidator;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User service.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDao userDao;
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
    @Value("${userRileService.incorrectUserName}")
    private String incorrectUserName;

    /**
     * Instantiates a new User service.
     *
     * @param userDao               the user dao
     * @param userValidator         the user validator
     * @param bCryptPasswordEncoder the b crypt password encoder
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator userValidator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Response getUsersAndCountByPageAndSize(Integer page, Integer size) {
        // TODO: validate page and size
        Integer offset = size * --page;
        Response<User> response = new Response<>();
        response.setData(userDao.getUsersByLimitAndOffset(size, offset));
        response.setPagination(new Pagination(this.getUserCount()));
        return response;
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
    public String getUserPasswordByUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new ValidationException(incorrectUserName);
        }
        return userDao.getUserPasswordByUserName(userName);
    }

    @Override
    public Long addUser(User user) {
        userValidator.validateExistingLogin(user.getName(), userDao);
        userValidator.validatePassword(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.addUser(user);
    }

    @Override
    public void updateUser(UserUpdatingDto userUpdatingDto) {
        userValidator.validatePassword(userUpdatingDto.getPassword());
        userUpdatingDto.setPassword(bCryptPasswordEncoder.encode(userUpdatingDto.getPassword()));
        if (userDao.updateUser(userUpdatingDto) == 0)
            throw new InternalServerException(updateError);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userId == null || userId < 0L)
            throw new ValidationException(incorrectId);
        if (userDao.deleteUser(userId) == 0)
            throw new InternalServerException(deleteError);
    }

    @Override
    public Long getUserCount() {
        return userDao.getUserCount();
    }

}
