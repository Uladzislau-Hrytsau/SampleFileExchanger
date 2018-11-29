package com.exchange.test.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Uladzislau Hrytsau on 20.11.18.
 */
public class UserTest {

    public static final Long USER_ID = 10L;

    public static final String LOGIN = "testLogin";

    public static final String PASSWORD = "testPassword";

    @Test
    public void getUserId() throws Exception {

        User user = new User();
        user.setUserId(10L);
        Assert.assertEquals("User id: ", USER_ID, user.getUserId());
    }

    @Test
    public void getLogin() throws Exception {

        User user = new User();
        user.setLogin("testLogin");
        Assert.assertEquals("User login: ", LOGIN, user.getLogin());
    }

    @Test
    public void getPassword() throws Exception {

        User user = new User();
        user.setPassword("testPassword");
        Assert.assertEquals("User password: ", PASSWORD, user.getPassword());
    }


}
