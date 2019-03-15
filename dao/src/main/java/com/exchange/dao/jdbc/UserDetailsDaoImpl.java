package com.exchange.dao.jdbc;

import com.exchange.dao.UserDetailsDao;
import com.exchange.dao.jdbc.mapper.dto.UserDetailsDtoRowMapper;
import com.exchange.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * The type User details dao.
 */
@Component
public class UserDetailsDaoImpl implements UserDetailsDao {

    @Value("${userDetails.selectUserDetailsByLogin}")
    private String selectUserDetailsByLoginSql;

    private JdbcTemplate jdbcTemplate;
    private UserDetailsDtoRowMapper userDetailsDtoRowMapper;

    /**
     * Instantiates a new User details dao.
     *
     * @param dataSource              the data source
     * @param userDetailsDtoRowMapper the user details dto row mapper
     */
    @Autowired
    public UserDetailsDaoImpl(DataSource dataSource, UserDetailsDtoRowMapper userDetailsDtoRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userDetailsDtoRowMapper = userDetailsDtoRowMapper;
    }

    @Override
    public UserDetailsDto getUserDetailsByLogin(String login) {
        return jdbcTemplate.queryForObject(selectUserDetailsByLoginSql, userDetailsDtoRowMapper, login);
    }

}
