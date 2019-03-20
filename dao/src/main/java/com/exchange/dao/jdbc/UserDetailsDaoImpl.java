package com.exchange.dao.jdbc;

import com.exchange.dao.UserDetailsDao;
import com.exchange.dao.jdbc.mapper.dto.UserDetailsDtoRowMapper;
import com.exchange.dto.security.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * The type User details dao.
 */
@Component
public class UserDetailsDaoImpl implements UserDetailsDao {

    private static final String USER_NAME = "user_name";

    @Value("${userDetails.selectUserDetailsByLogin}")
    private String selectUserDetailsByLoginSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UserDetailsDtoRowMapper userDetailsDtoRowMapper;

    /**
     * Instantiates a new User details dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param userDetailsDtoRowMapper    the user details dto row mapper
     */
    @Autowired
    public UserDetailsDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserDetailsDtoRowMapper userDetailsDtoRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userDetailsDtoRowMapper = userDetailsDtoRowMapper;
    }

    @Override
    public UserDetailsDto getUserDetailsByLogin(String login) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_NAME, login);
        return namedParameterJdbcTemplate.queryForObject(selectUserDetailsByLoginSql, parameterSource, userDetailsDtoRowMapper);
    }

}
