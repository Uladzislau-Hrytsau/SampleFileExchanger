package com.exchange.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type User role dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class UserRoleDaoImplTest {

    @Autowired
    private UserRoleDao userRoleDao;

    private static final String USERNAME = "userLogin1";

    /**
     * Gets roles by user name test.
     */
    @Test
    public void getRolesByUserNameTest() {
        List<String> roles = userRoleDao.getRolesByUserName(USERNAME);
        assertNotNull(roles);
        assertEquals(2, roles.size());
    }

}
