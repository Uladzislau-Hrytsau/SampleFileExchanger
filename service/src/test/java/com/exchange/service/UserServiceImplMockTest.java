package com.exchange.service;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.service.validator.Validator;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mentor on 20.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test-mock.xml"})
public class UserServiceImplMockTest {

    private static final User user = new User("userLogin1", "userPassword1");

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDaoMock;

    @After
    public void clean() {
//        verify(mockUserDao);
//        reset(mockUserDao);
    }


    @Test
    public void testAddUser() throws Exception {
    }

    @Test//(expected = UnsupportedOperationException.class)
    public void testGetUserByLoginException() {
    }

}
