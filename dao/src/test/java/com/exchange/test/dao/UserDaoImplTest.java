package com.exchange.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

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

    private static final User user_3 = new User(
            3L, "userLogin3", "userPassword3",
            false, LocalDate.of(2000, 12, 20),
            "information3");
    private static final User user_4 = new User(
            4L, "userLogin4", "userPassword4",
            false, LocalDate.of(2000, 12, 20),
            "information4");

    @Autowired
    UserDao userDao;

    @Test
    public void getAllUsersTest() {
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void getUserByUserIdTest() {
        User user = userDao.getUserByUserId(1L);
        assertNotNull(user);
        assertEquals(USER_ID_1, user.getUserId());
        assertEquals(USER_LOGIN_1, user.getLogin());
        assertEquals(USER_PASSWORD_1, user.getPassword());
        assertEquals(USER_GENDER_1, user.getGender());
        assertEquals(USER_BIRTH_DATE_1, user.getBirthDate());
        assertEquals(USER_INFORMATION_1, user.getInformation());
    }

    @Test
    public void getUserByLoginTest() {
        User user = userDao.getUserByLogin(USER_LOGIN_1);
        assertNotNull(user);
        assertEquals(USER_ID_1, user.getUserId());
        assertEquals(USER_LOGIN_1, user.getLogin());
        assertEquals(USER_PASSWORD_1, user.getPassword());
        assertEquals(USER_GENDER_1, user.getGender());
        assertEquals(USER_BIRTH_DATE_1, user.getBirthDate());
        assertEquals(USER_INFORMATION_1, user.getInformation());
    }

    @Test
    public void addUserTest() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        int quantityBefore = users.size();

        Long userId = userDao.addUser(user_3);
        assertNotNull(userId);

        User newUser = userDao.getUserByUserId(userId);
        assertNotNull(newUser);
        assertEquals(user_3.getUserId(), newUser.getUserId());
        assertEquals(user_3.getLogin(), newUser.getLogin());
        assertEquals(user_3.getPassword(), newUser.getPassword());
        assertEquals(user_3.getGender(), newUser.getGender());
        assertEquals(user_3.getBirthDate(), newUser.getBirthDate());
        assertEquals(user_3.getInformation(), newUser.getInformation());

        users = userDao.getAllUsers();
        assertNotNull(users);
        assertEquals(quantityBefore + 1, users.size());
    }

    @Test
    public void updateUserTest() {

        User user = userDao.getUserByUserId(2L);
        assertNotNull(user);

        user.setLogin("updatedLogin21312");
        user.setPassword("updatedPassword");
        user.setGender(true);
        user.setBirthDate(LocalDate.of(1111, 11, 11));
        user.setInformation("updatedInformation");

        int count = userDao.updateUser(user);
        assertEquals(1, count);

        User updatedUser = userDao.getUserByUserId(user.getUserId());
        assertEquals(user.getLogin(), updatedUser.getLogin());
        assertEquals(user.getPassword(), updatedUser.getPassword());
        assertEquals(user.getGender(), updatedUser.getGender());
        assertEquals(user.getBirthDate(), updatedUser.getBirthDate());
        assertEquals(user.getInformation(), updatedUser.getInformation());
    }

    @Test
    public void deleteUserTest() {
        Long userId = userDao.addUser(user_4);
        assertNotNull(userId);

        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        int  quantityBefore = users.size();

        int count = userDao.deleteUser(userId);
        assertEquals(1, count);

        users = userDao.getAllUsers();
        assertNotNull(users);
        assertEquals(quantityBefore - 1, users.size());
    }
}
