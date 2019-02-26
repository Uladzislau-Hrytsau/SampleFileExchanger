package com.exchange.dao.jdbc;

import com.exchange.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
     * @param dataSource the data source
     */
    public UserRoleDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String> getRolesByUserName(String userName) {
        return jdbcTemplate.queryForList(selectRolesByUserNameSql, String.class, userName);
    }

}
