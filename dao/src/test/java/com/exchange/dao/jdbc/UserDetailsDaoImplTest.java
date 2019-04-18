package com.exchange.dao.jdbc;

import com.exchange.dao.TestSpringDaoConfiguration;
import com.exchange.dao.UserDetailsDao;
import com.exchange.dto.security.UserDetailsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;

/**
 * The type User details dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDetailsDaoImplTest {

    private static final String CORRECT_LOGIN = "user1";
    private static final String INCORRECT_LOGIN = "userDoesNotExist";
    private static final UserDetailsDto CORRECT_USER_DETAILS_DTO = new UserDetailsDto(
            1L,
            CORRECT_LOGIN,
            "password1",
            new HashSet<>(Arrays.asList(
                    "ROLE_USER",
                    "ROLE_ADMIN"
            ))
    );

    @Autowired
    private UserDetailsDao userDetailsDao;

    /**
     * Gets user details by login with correct login then correct user details dto returned.
     */
    @Test
    public void getUserDetailsByLoginWithCorrectLoginThenCorrectUserDetailsDtoReturned() {
        UserDetailsDto actualUserDetailsByLogin = userDetailsDao.getUserDetailsByLogin(CORRECT_LOGIN);
        Assert.assertEquals(CORRECT_USER_DETAILS_DTO, actualUserDetailsByLogin);
    }

    /**
     * Gets user details by login with incorrect login then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserDetailsByLoginWithIncorrectLoginThenThrowEmptyResultDataAccessException() {
        userDetailsDao.getUserDetailsByLogin(INCORRECT_LOGIN);
    }

    /**
     * Gets user details by login with empty login then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserDetailsByLoginWithEmptyLoginThenThrowEmptyResultDataAccessException() {
        userDetailsDao.getUserDetailsByLogin("");
    }

    /**
     * Gets user details by login with null login then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserDetailsByLoginWithNullLoginThenThrowEmptyResultDataAccessException() {
        userDetailsDao.getUserDetailsByLogin(null);
    }

}
