package com.exchange.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type User dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class UserDaoImplTest {

    private static final Long USER_ID_1 = 1L;
    private static final String USER_LOGIN_1 = "userLogin1";
    private static final String USER_PASSWORD_1 = "userPassword1";
    private static final Boolean USER_GENDER_1 = true;
    private static final LocalDate USER_BIRTH_DATE_1 = LocalDate.of(1000, 10, 10);
    private static final String USER_INFORMATION_1 = "userInformation1";

    private static final Long USER_ID_2 = 2L;

    private static final User user_3 = new User(
            3L, "getUserPasswordByUserName", "userPassword3",
            false, LocalDate.of(8888, 8, 8),
            "userInformation2"
    );
    private static final User user_4 = new User(
            4L, "userLogin4", "userPassword4",
            true, LocalDate.of(7777, 7, 7),
            "userInformation4"
    );

    @Autowired
    private UserDao userDao;

    /**
     * Gets all users test.
     */
    @Test
    public void getAllUsersTest() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    /**
     * Gets user by user id test.
     */
    @Test
    public void getUserByUserIdTest() {
        User user = userDao.getUserByUserId(USER_ID_1);
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertNotNull(user.getLogin());
        assertNotNull(user.getPassword());
        assertNotNull(user.getGender());
        assertNotNull(user.getBirthDate());
        assertNotNull(user.getInformation());
        assertEquals(USER_ID_1, user.getUserId());
        assertEquals(USER_LOGIN_1, user.getLogin());
        assertEquals(USER_PASSWORD_1, user.getPassword());
        assertEquals(USER_GENDER_1, user.getGender());
        assertEquals(USER_BIRTH_DATE_1, user.getBirthDate());
        assertEquals(USER_INFORMATION_1, user.getInformation());
    }

    /**
     * Gets user by login test.
     */
    @Test
    public void getUserByLoginTest() {
        User user = userDao.getUserByLogin(USER_LOGIN_1);
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertNotNull(user.getLogin());
        assertNotNull(user.getPassword());
        assertNotNull(user.getGender());
        assertNotNull(user.getBirthDate());
        assertNotNull(user.getInformation());
        assertEquals(USER_ID_1, user.getUserId());
        assertEquals(USER_LOGIN_1, user.getLogin());
        assertEquals(USER_PASSWORD_1, user.getPassword());
        assertEquals(USER_GENDER_1, user.getGender());
        assertEquals(USER_BIRTH_DATE_1, user.getBirthDate());
        assertEquals(USER_INFORMATION_1, user.getInformation());
    }

    /**
     * Gets user password by user name test.
     */
    @Test
    public void getUserPasswordByUserNameTest() {
        String login = "loginForgetUserPassword";
        String password = "passwordForgetUserPassword";
        User user = new User(login, password);
        assertNotNull(user);
        Long id = userDao.addUser(user);
        assertNotNull(id);
        String passwordAfterGetUserPasswordByUserName = userDao.getUserPasswordByUserName(login);
        assertNotNull(passwordAfterGetUserPasswordByUserName);
        assertEquals(password, passwordAfterGetUserPasswordByUserName);
    }

    /**
     * Add user test.
     */
    @Test
    public void addUserTest() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        int quantityBefore = users.size();

        Long id = userDao.addUser(user_3);
        assertNotNull(id);

        User newUser = userDao.getUserByUserId(id);
        assertNotNull(newUser);

        assertEquals(user_3.getUserId(), newUser.getUserId());
        assertEquals(user_3.getLogin(), newUser.getLogin());
        assertEquals(user_3.getPassword(), newUser.getPassword());
        assertEquals(user_3.getGender(), newUser.getGender());
        assertEquals(user_3.getBirthDate(), newUser.getBirthDate());
        assertEquals(user_3.getInformation(), newUser.getInformation());

        users = userDao.getAllUsers();
        assertEquals(quantityBefore + 1, users.size());
    }

    /**
     * Update user test.
     */
    @Test
    public void updateUserTest() {
        User user = userDao.getUserByUserId(USER_ID_2);
        assertNotNull(user);

        user.setLogin("updatedLogin");
        user.setPassword("updatedPassword");
        user.setGender(true);
        user.setBirthDate(LocalDate.of(1111, 11, 11));
        user.setInformation("updatedInformation");

        int count = userDao.updateUser(user);
        assertEquals(1, count);

        User updatedUser = userDao.getUserByUserId(user.getUserId());
        assertNotNull(updatedUser);

        assertEquals(user.getLogin(), updatedUser.getLogin());
        assertEquals(user.getPassword(), updatedUser.getPassword());
        assertEquals(user.getGender(), updatedUser.getGender());
        assertEquals(user.getBirthDate(), updatedUser.getBirthDate());
        assertEquals(user.getInformation(), updatedUser.getInformation());
    }

    /**
     * Delete user test.
     */
    @Test
    public void deleteUserTest() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        int quantityBefore = users.size();

        Long id = userDao.addUser(user_4);
        assertNotNull(id);

        User newUser = userDao.getUserByUserId(id);
        assertNotNull(newUser);

        assertEquals(user_4.getUserId(), newUser.getUserId());
        assertEquals(user_4.getLogin(), newUser.getLogin());
        assertEquals(user_4.getPassword(), newUser.getPassword());
        assertEquals(user_4.getGender(), newUser.getGender());
        assertEquals(user_4.getBirthDate(), newUser.getBirthDate());
        assertEquals(user_4.getInformation(), newUser.getInformation());

        users = userDao.getAllUsers();
        assertEquals(quantityBefore + 1, users.size());

        int count = userDao.deleteUser(id);
        users = userDao.getAllUsers();
        assertNotNull(users);
        assertEquals(1, count);
        assertEquals(quantityBefore, users.size());
    }

    /**
     * Check user by user id test.
     */
    @Test
    public void checkUserByUserIdTest() {
        assertTrue(userDao.checkUserByUserId(USER_ID_1));
    }

    /**
     * Check user by login test.
     */
    @Test
    public void checkUserByLoginTest() {
        assertTrue(userDao.checkUserByLogin(USER_LOGIN_1));
    }

    /**
     * Gets user id by login test.
     */
    @Test
    public void getUserIdByLoginTest() {
        String userName = "loginForTest";
        String password = "1233214562w";
        User user = new User(userName, password);
        Long id = userDao.addUser(user);
        assertNotNull(id);
        Long newId = userDao.getUserIdByLogin(userName);
        assertNotNull(newId);
        assertEquals(id, newId);
    }
}
