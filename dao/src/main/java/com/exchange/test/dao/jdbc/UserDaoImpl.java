package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.User;
import com.exchange.test.dao.UserDao;
import com.exchange.test.dao.jdbc.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserDao implementation.
 */
public class UserDaoImpl implements UserDao {

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_BIRTH_DATE = "user_birth_date";
    public static final String USER_INFORMATION = "user_information";

    private UserRowMapper userRowMapper = new UserRowMapper();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.select}")
    private String getAllUsersSql;

    @Value("${user.selectByUserId}")
    private String selectUserByUserIdSql;

    @Value("${user.selectByLogin}")
    private String selectByUserLoginSql;

    @Value("${user.insert}")
    private String insertUserSql;

    @Value("${user.update}")
    private String updateUserSql;

    @Value("${user.delete}")
    private String deleteUserSql;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<User> getAllUsers() throws DataAccessException {
        return jdbcTemplate.query(getAllUsersSql, userRowMapper);
    }

    public User getUserByUserId(Long userId) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(
                selectUserByUserIdSql, namedParameters, userRowMapper
        );
    }

    public User getUserByLogin(String userName) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_name", userName);
        return namedParameterJdbcTemplate.queryForObject(
                selectByUserLoginSql, namedParameters, userRowMapper
        );
    }

    public Long addUser(User user) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, user.getUserId());
        parameterSource.addValue(USER_NAME, user.getLogin());
        parameterSource.addValue(USER_PASSWORD, user.getPassword());
        parameterSource.addValue(USER_GENDER, user.getGender());
        parameterSource.addValue(USER_BIRTH_DATE, user.getBirthDate());
        parameterSource.addValue(USER_INFORMATION, user.getInformation());
        namedParameterJdbcTemplate.update(
                insertUserSql, parameterSource, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public int updateUser(User user) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(USER_ID, user.getUserId());
        params.put(USER_NAME, user.getLogin());
        params.put(USER_PASSWORD, user.getPassword());
        params.put(USER_GENDER, user.getGender());
        params.put(USER_BIRTH_DATE, user.getBirthDate());
        params.put(USER_INFORMATION, user.getInformation());
        return namedParameterJdbcTemplate.update(updateUserSql, params);
    }

    public int deleteUser(Long userId) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(USER_ID, userId);
        return namedParameterJdbcTemplate.update(deleteUserSql, params);
    }
}
