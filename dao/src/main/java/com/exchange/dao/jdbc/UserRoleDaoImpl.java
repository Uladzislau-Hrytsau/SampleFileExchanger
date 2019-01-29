package com.exchange.dao.jdbc;

import com.exchange.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The type User role dao.
 */
@Component
public class UserRoleDaoImpl implements UserRoleDao {

    @Value("${userRole.selectRolesByUserId}")
    private String selectRolesByUserIdSql;

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
    public Set<String> getRolesByUserId(Long userId) {

        Set<String> role = new HashSet<>();

        List<String> list = jdbcTemplate.queryForList(selectRolesByUserIdSql, String.class, userId);

        Stream stream = list.stream();
        stream.forEach(element -> role.add(element.toString()));

        return role;
    }

}
