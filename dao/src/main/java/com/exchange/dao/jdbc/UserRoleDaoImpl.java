package com.exchange.dao.jdbc;

import com.exchange.dao.UserRoleDao;
import com.exchange.dao.jdbc.mapper.UserRoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User role dao.
 */
@Component
public class UserRoleDaoImpl implements UserRoleDao {

    @Value("${userRole.selectByUserId}")
    private String selectByUserIdSql;

    @Value("${userRole.selectByRoleId}")
    private String selectByRoleIdSql;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRoleRowMapper userRoleRowMapper;

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

//        Set<String> role = new HashSet<>();
//        role.add("ROLE_USER");
//        role.add("ROLE_ADMIN");
//        HashSet<String> set =  jdbcTemplate.queryForObject(selectByUserIdSql, new Long[]{userId}, HashSet.class);
        Long[] array = jdbcTemplate.queryForObject(selectByUserIdSql, new Long[]{userId}, Long[].class);
        return jdbcTemplate.queryForObject(selectByRoleIdSql, array, HashSet.class);
    }

    @Override
    public Set<String> setRolesByUserId(Long userId) {
        return null;
    }

}
