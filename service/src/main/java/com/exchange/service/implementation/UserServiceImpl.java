package com.exchange.service.implementation;

import com.exchange.dao.Pagination;
import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.FileService;
import com.exchange.service.FileWriterService;
import com.exchange.service.RoleService;
import com.exchange.service.UserService;
import com.exchange.service.validation.CommonValidator;
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
    private final CommonService commonService;
    private final CommonValidator commonValidator;
    private final FileService fileService;
    private final FileWriterService fileWriterService;

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
     * @param commonService         the common service
     * @param commonValidator       the common validator
     * @param fileService           the file service
     * @param fileWriterService     the file writer service
     */
    @Autowired
    public UserServiceImpl(
            UserDao userDao,
            UserValidator userValidator,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RoleService roleService,
            CommonService commonService,
            CommonValidator commonValidator,
            FileService fileService,
            FileWriterService fileWriterService) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.commonService = commonService;
        this.commonValidator = commonValidator;
        this.fileService = fileService;
        this.fileWriterService = fileWriterService;
    }

    @Override
    public Response getUsersAndCountByPageAndSize(Integer page, Integer size) {
        commonValidator.validatePageAndSize(page, size);
        Response<User> response = new Response<>();
        response.setData(userDao.getUsersByLimitAndOffset(size, commonService.getOffsetBySizeAndPage(size, page)));
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
        userValidator.validateInformation(userUpdatingDto.getInformation());
        commonValidator.validateDate(userUpdatingDto.getBirthDate());
        userUpdatingDto.setPassword(bCryptPasswordEncoder.encode(userUpdatingDto.getPassword()));
        if (userDao.updateUser(userUpdatingDto) == 0)
            throw new InternalServerException(updateError);
    }

    @Override
    public void deleteUser(Long userId) {
        userValidator.validateUserId(userId);
        fileWriterService.deleteFilesByNames(fileService.getFileNamesByUserId(userId));
        if (userDao.deleteUser(userId) == 0)
            throw new InternalServerException(deleteError);
    }

    @Override
    public Long getUserCount() {
        return userDao.getUserCount();
    }

}
