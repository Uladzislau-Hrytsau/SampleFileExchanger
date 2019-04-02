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

    @Test
    public void getUserDetailsByLogin_correctLogin_correctUserDetailsDtoReturned() {
        UserDetailsDto actualUserDetailsByLogin = userDetailsDao.getUserDetailsByLogin(CORRECT_LOGIN);
        Assert.assertEquals(CORRECT_USER_DETAILS_DTO, actualUserDetailsByLogin);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserDetailsByLogin_incorrectLogin_emptyResultDataAccessExceptionReturned() {
        userDetailsDao.getUserDetailsByLogin(INCORRECT_LOGIN);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserDetailsByLogin_emptyLogin_emptyResultDataAccessExceptionReturned() {
        userDetailsDao.getUserDetailsByLogin("");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserDetailsByLogin_nullLogin_emptyResultDataAccessExceptionReturned() {
        userDetailsDao.getUserDetailsByLogin(null);
    }

}
