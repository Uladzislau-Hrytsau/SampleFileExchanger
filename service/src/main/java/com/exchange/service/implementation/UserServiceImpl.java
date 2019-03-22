package com.exchange.service.implementation;

import com.exchange.dao.Pagination;
import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.RoleService;
import com.exchange.service.UserService;
import com.exchange.service.validation.user.UserValidator;
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

    private static final int USER_ROLE_ID = 1;
    @Lazy
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDao userDao;
    private final UserValidator userValidator;
    private final RoleService roleService;

    @Value("${userService.deleteError}")
    private String deleteError;
    @Value("${userService.updateError}")
    private String updateError;
    @Value("${userService.createError}")
    private String createError;
    @Value("${userService.incorrectId}")
    private String incorrectId;

    /**
     * Instantiates a new User service.
     *
     * @param userDao               the user dao
     * @param userValidator         the user validator
     * @param bCryptPasswordEncoder the b crypt password encoder
     * @param roleService           the role service
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator userValidator, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
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
    public void addUser(User user) {
        userValidator.validateExistingLogin(user.getName(), userDao);
        userValidator.validatePassword(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Long userId = userDao.addUser(user);
        if (userId == null) {
            throw new InternalServerException(createError);
        }
        roleService.addUserRole(userId, USER_ROLE_ID);
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
        // TODO: delete file_category by file_id

        // TODO: delete user_has_category by user_id
        // TODO: delete file by user_id
        // TODO: delete folder by user_id
        // TODO: delete user_role by user_id
        if (userId == null || userId < 0L) {
            throw new ValidationException(incorrectId);
        }


        if (userDao.deleteUser(userId) == 0)
            throw new InternalServerException(deleteError);
    }

    @Override
    public Long getUserCount() {
        return userDao.getUserCount();
    }

}
