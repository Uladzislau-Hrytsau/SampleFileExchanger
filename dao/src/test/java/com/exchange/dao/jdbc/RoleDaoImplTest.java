package com.exchange.dao.jdbc;

import com.exchange.dao.RoleDao;
import com.exchange.dao.TestSpringDaoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Role dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoleDaoImplTest {

    private static final Long CORRECT_USER_ID = 2L;
    private static final Long INCORRECT_USER_ID = -2L;
    private static final Integer CORRECT_ROLE_ID = 2;
    private static final Integer INCORRECT_ROLE_ID = -2;

    @Value("${role.getRolesByUserId}")
    private String getRolesByUserIdSql;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Add user role with correct user id and role id then true returned.
     */
    @Test
    public void addUserRoleWithCorrectUserIdAndRoleIdThenTrueReturned() {
        List<Integer> actualRolesByUserIdBeforeAdding = this.getRolesByUserId(CORRECT_USER_ID);
        Boolean addingUserRoleResult = roleDao.addUserRole(CORRECT_USER_ID, CORRECT_ROLE_ID);
        List<Integer> actualRolesByUserIdAfterAdding = this.getRolesByUserId(CORRECT_USER_ID);
        assertTrue(addingUserRoleResult);
        assertNotNull(actualRolesByUserIdBeforeAdding);
        assertNotNull(actualRolesByUserIdAfterAdding);
        assertFalse(actualRolesByUserIdBeforeAdding.contains(CORRECT_ROLE_ID));
        assertTrue(actualRolesByUserIdAfterAdding.contains(CORRECT_ROLE_ID));
    }

    /**
     * Add user role with incorrect user id and correct role id then throw data integrity violation exception.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void addUserRoleWithIncorrectUserIdAndCorrectRoleIdThenThrowDataIntegrityViolationException() {
        roleDao.addUserRole(CORRECT_USER_ID, INCORRECT_ROLE_ID);
    }

    /**
     * Add user role with correct user id and incorrect role id then throw data integrity violation exception.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void addUserRoleWithCorrectUserIdAndIncorrectRoleIdThenThrowDataIntegrityViolationException() {
        roleDao.addUserRole(INCORRECT_USER_ID, CORRECT_ROLE_ID);
    }

    /**
     * Add user role with null user id and role id then throw data integrity violation exception.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void addUserRoleWithNullUserIdAndRoleIdThenThrowDataIntegrityViolationException() {
        roleDao.addUserRole(null, null);
    }

    private List<Integer> getRolesByUserId(Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForList(getRolesByUserIdSql, parameterSource, Integer.class);
    }

}
