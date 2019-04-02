package com.exchange.dao.jdbc;

import com.exchange.dao.TestSpringDaoConfiguration;
import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dto.user.UserUpdatingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type User dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDaoImplTest {

    private static final Integer CORRECT_LIMIT = 2;
    private static final Integer INCORRECT_LIMIT = -1;
    private static final Integer CORRECT_OFFSET = 0;
    private static final Integer INCORRECT_OFFSET = -10;

    private static final User CORRECT_USER_ONE = new User(
            1L,
            "user1",
            "password1",
            true,
            "information1",
            LocalDate.of(1000, 10, 10)
    );
    private static final User CORRECT_USER_TWO = new User(
            2L,
            "user2",
            "password2",
            false,
            "information2",
            LocalDate.of(1000, 11, 10)
    );
    private static final User CORRECT_USER_THREE = new User(
            3L,
            "user3",
            "password3",
            true,
            "information3",
            LocalDate.of(1001, 10, 10)
    );
    private static final User STILL_NOT_EXIST_USER = new User(
            4L,
            "user4",
            "password4",
            true,
            "information4",
            LocalDate.of(1111, 11, 11)
    );
    private static final User ALREADY_EXISTS_USER = CORRECT_USER_ONE;
    private static final UserUpdatingDto CORRECT_USER_UPDATING_DTO = new UserUpdatingDto(
            CORRECT_USER_ONE.getId(),
            "updatedPassword",
            false,
            "updatedInformation",
            LocalDate.of(1111, 11, 11)
    );
    private static final UserUpdatingDto INCORRECT_USER_UPDATING_DTO = new UserUpdatingDto();

    @Value("${user.getUserByUserId}")
    private String getUserByUserIdSql;

    @Autowired
    private UserDao userDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets users by limit and offset correct limit and offset correct users returned.
     */
    @Test
    public void getUsersByLimitAndOffset_correctLimitAndOffset_correctUsersReturned() {
        List<User> usersByLimitAndOffset = userDao.getUsersByLimitAndOffset(CORRECT_LIMIT, CORRECT_OFFSET);
        Integer actualSize = usersByLimitAndOffset.size();
        assertEquals(CORRECT_LIMIT, actualSize);
        assertTrue(usersByLimitAndOffset.containsAll(Arrays.asList(CORRECT_USER_ONE, CORRECT_USER_TWO)));
    }

    /**
     * Gets users by limit and offset incorrect limit and correct offset all users returned.
     */
    @Test
    public void getUsersByLimitAndOffset_incorrectLimitAndCorrectOffset_allUsersReturned() {
        List<User> usersByLimitAndOffset = userDao.getUsersByLimitAndOffset(INCORRECT_LIMIT, CORRECT_OFFSET);
        Integer actualSize = usersByLimitAndOffset.size();
        Integer expectedSize = Math.toIntExact(userDao.getUserCount());
        assertEquals(expectedSize, actualSize);
        assertTrue(usersByLimitAndOffset.containsAll(Arrays.asList(CORRECT_USER_ONE, CORRECT_USER_TWO, CORRECT_USER_THREE)));
    }

    /**
     * Gets users by limit and offset correct limit and incorrect offset correct users by limit with offset zero returned.
     */
    @Test
    public void getUsersByLimitAndOffset_correctLimitAndIncorrectOffset_correctUsersByLimitWithOffsetZeroReturned() {
        List<User> usersByLimitAndOffset = userDao.getUsersByLimitAndOffset(CORRECT_LIMIT, INCORRECT_OFFSET);
        Integer actualSize = usersByLimitAndOffset.size();
        assertEquals(CORRECT_LIMIT, actualSize);
        assertTrue(usersByLimitAndOffset.containsAll(Arrays.asList(CORRECT_USER_ONE, CORRECT_USER_TWO)));
        assertTrue(usersByLimitAndOffset.containsAll(Arrays.asList(CORRECT_USER_ONE, CORRECT_USER_TWO)));
    }

    /**
     * Gets users by limit and offset null limit and offset all users returned.
     */
    @Test
    public void getUsersByLimitAndOffset_nullLimitAndOffset_allUsersReturned() {
        List<User> usersByLimitAndOffset = userDao.getUsersByLimitAndOffset(null, null);
        Integer actualSize = usersByLimitAndOffset.size();
        Integer expectedSize = Math.toIntExact(userDao.getUserCount());
        assertEquals(expectedSize, actualSize);
        assertTrue(usersByLimitAndOffset.containsAll(Arrays.asList(CORRECT_USER_ONE, CORRECT_USER_TWO, CORRECT_USER_THREE)));
    }

    /**
     * Add user correct user correct user returned.
     */
    @Test
    public void addUser_correctUser_correctUserReturned() {
        Long actualUserId = userDao.addUser(STILL_NOT_EXIST_USER);
        User userByUserIdAfterAdding = this.getUserByUserId(actualUserId);
        assertNotNull(userByUserIdAfterAdding);
        assertEquals(STILL_NOT_EXIST_USER, userByUserIdAfterAdding);
    }

    /**
     * Add user already exists user duplicate key exception returned.
     */
    @Test(expected = DuplicateKeyException.class)
    public void addUser_alreadyExistsUser_duplicateKeyExceptionReturned() {
        userDao.addUser(ALREADY_EXISTS_USER);
    }

    /**
     * Add user incorrect exists user null pointer exception returned.
     */
    @Test(expected = NullPointerException.class)
    public void addUser_incorrectExistsUser_nullPointerExceptionReturned() {
        userDao.addUser(null);
    }

    /**
     * Update user correct user updating dto true returned.
     */
    @Test
    public void updateUser_correctUserUpdatingDto_trueReturned() {
        User userBeforeUpdating = this.getUserByUserId(CORRECT_USER_UPDATING_DTO.getId());
        Boolean updatingUserResult = userDao.updateUser(CORRECT_USER_UPDATING_DTO);
        User userAfterUpdating = this.getUserByUserId(CORRECT_USER_UPDATING_DTO.getId());
        assertTrue(updatingUserResult);
        assertNotNull(userBeforeUpdating);
        assertNotNull(userAfterUpdating);
        assertEquals(userAfterUpdating.getId(), CORRECT_USER_UPDATING_DTO.getId());
        assertNotEquals(userBeforeUpdating.getBirthDate(), CORRECT_USER_UPDATING_DTO.getBirthDate());
        assertNotEquals(userBeforeUpdating.getGender(), CORRECT_USER_UPDATING_DTO.getGender());
        assertNotEquals(userBeforeUpdating.getInformation(), CORRECT_USER_UPDATING_DTO.getInformation());
        assertNotEquals(userBeforeUpdating.getPassword(), CORRECT_USER_UPDATING_DTO.getPassword());
        assertEquals(userAfterUpdating.getBirthDate(), CORRECT_USER_UPDATING_DTO.getBirthDate());
        assertEquals(userAfterUpdating.getGender(), CORRECT_USER_UPDATING_DTO.getGender());
        assertEquals(userAfterUpdating.getInformation(), CORRECT_USER_UPDATING_DTO.getInformation());
        assertEquals(userAfterUpdating.getPassword(), CORRECT_USER_UPDATING_DTO.getPassword());
    }

    /**
     * Update user incorrect user updating dto false returned.
     */
    @Test
    public void updateUser_incorrectUserUpdatingDto_falseReturned() {
        Boolean updatingUserResult = userDao.updateUser(INCORRECT_USER_UPDATING_DTO);
        assertFalse(updatingUserResult);
    }

    /**
     * Update user null user updating dto false returned.
     */
    @Test(expected = NullPointerException.class)
    public void updateUser_nullUserUpdatingDto_falseReturned() {
        userDao.updateUser(null);
    }

    /**
     * Delete user correct user id true returned.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteUser_correctUserId_trueReturned() {
        User userByUserIdBeforeDeleting = this.getUserByUserId(CORRECT_USER_ONE.getId());
        Boolean deletingResult = userDao.deleteUser(CORRECT_USER_ONE.getId());
        assertTrue(deletingResult);
        assertNotNull(userByUserIdBeforeDeleting);
        this.getUserByUserId(CORRECT_USER_ONE.getId());
    }

    /**
     * Delete user incorrect user id false returned.
     */
    @Test
    public void deleteUser_incorrectUserId_falseReturned() {
        Boolean deletingResult = userDao.deleteUser(STILL_NOT_EXIST_USER.getId());
        assertFalse(deletingResult);
    }

    /**
     * Delete user null user id false returned.
     */
    @Test
    public void deleteUser_nullUserId_falseReturned() {
        Boolean deletingResult = userDao.deleteUser(null);
        assertFalse(deletingResult);
    }

    /**
     * Check user by login correct user name true returned.
     */
    @Test
    public void checkUserByLogin_correctUserName_trueReturned() {
        Boolean checkingUserByLoginResult = userDao.checkUserByLogin(CORRECT_USER_ONE.getName());
        assertTrue(checkingUserByLoginResult);
        User existingUserByUserId = this.getUserByUserId(CORRECT_USER_ONE.getId());
        assertNotNull(existingUserByUserId);
    }

    /**
     * Check user by login incorrect user name false returned.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void checkUserByLogin_incorrectUserName_falseReturned() {
        Boolean checkingUserByLoginResult = userDao.checkUserByLogin(STILL_NOT_EXIST_USER.getName());
        assertFalse(checkingUserByLoginResult);
        this.getUserByUserId(STILL_NOT_EXIST_USER.getId());
    }

    /**
     * Check user by login empty user name false returned.
     */
    @Test
    public void checkUserByLogin_emptyUserName_falseReturned() {
        Boolean checkingUserByLoginResult = userDao.checkUserByLogin("");
        assertFalse(checkingUserByLoginResult);
    }

    /**
     * Check user by login null user name false returned.
     */
    @Test
    public void checkUserByLogin_nullUserName_falseReturned() {
        Boolean checkingUserByLoginResult = userDao.checkUserByLogin(null);
        assertFalse(checkingUserByLoginResult);
    }

    /**
     * Gets user count correct count users returned.
     */
    @Test
    public void getUserCount_correctCountUsersReturned() {
        Long userCountBeforeAddingUser = userDao.getUserCount();
        userDao.addUser(STILL_NOT_EXIST_USER);
        Long userCountAfterAddingUser = userDao.getUserCount();
        assertEquals(++userCountBeforeAddingUser, userCountAfterAddingUser);
    }

    private User getUserByUserId(Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", userId);
        return namedParameterJdbcTemplate.queryForObject(getUserByUserIdSql, parameterSource, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("user_name"));
            user.setPassword(rs.getString("user_password"));
            user.setGender(rs.getBoolean("user_gender"));
            user.setInformation(rs.getString("user_information"));
            Date date = rs.getDate("user_birth_date");
            if (date != null) {
                user.setBirthDate(date.toLocalDate());
            }
            return user;
        });
    }

}
