package com.exchange.dao.jdbc;

import com.exchange.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type User role dao.
 */
@Component
public class UserRoleDaoImpl implements UserRoleDao {

    @Value("${userRole.selectRolesByUserName}")
    private String selectRolesByUserNameSql;

    private JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new User role dao.
     *
     * @param jdbcTemplate the jdbc template
     */
    public UserRoleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getRolesByUserName(String userName) {
        return jdbcTemplate.queryForList(selectRolesByUserNameSql, String.class, userName);
    }

}
