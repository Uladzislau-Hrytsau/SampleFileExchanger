package com.exchange.dao.jdbc;

import com.exchange.dao.UserDetailsDao;
import com.exchange.dao.jdbc.mapper.dto.UserDetailsDtoRowMapper;
import com.exchange.dto.security.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type User details dao.
 */
@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

    public static final String ID = "id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_ROLE = "role";

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
    public UserDetailsDaoImpl(
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            final UserDetailsDtoRowMapper userDetailsDtoRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userDetailsDtoRowMapper = userDetailsDtoRowMapper;
    }

    @Override
    public UserDetailsDto getUserDetailsByLogin(final String login) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_NAME, login);
        return namedParameterJdbcTemplate.queryForObject(selectUserDetailsByLoginSql, parameterSource, userDetailsDtoRowMapper);
    }
}
