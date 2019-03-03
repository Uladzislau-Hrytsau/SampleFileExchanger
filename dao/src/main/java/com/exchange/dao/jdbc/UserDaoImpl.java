package com.exchange.dao.jdbc;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dao.jdbc.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type User dao.
 */
@Component
public class UserDaoImpl implements UserDao {

    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "user_id";
    /**
     * The constant USER_NAME.
     */
    public static final String USER_NAME = "user_name";
    /**
     * The constant USER_PASSWORD.
     */
    public static final String USER_PASSWORD = "user_password";
    /**
     * The constant USER_GENDER.
     */
    public static final String USER_GENDER = "user_gender";
    /**
     * The constant USER_BIRTH_DATE.
     */
    public static final String USER_BIRTH_DATE = "user_birth_date";
    /**
     * The constant USER_INFORMATION.
     */
    public static final String USER_INFORMATION = "user_information";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UserRowMapper userRowMapper;

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

    @Value("${user.checkUserByLogin}")
    private String checkUserByLoginSql;

    @Value("${user.checkUserByUserId}")
    private String checkUserByUserIdSql;

    @Value("${user.selectUserIdByLogin}")
    private String getUserIdByLoginSql;

    @Value("${user.selectUserPasswordByUserName}")
    private String selectUserPasswordByUserNameSql;

    /**
     * Instantiates a new User dao.
     *
     * @param dataSource    the data source
     * @param userRowMapper the user row mapper
     */
    public UserDaoImpl(DataSource dataSource, UserRowMapper userRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(getAllUsersSql, userRowMapper);
    }

    @Override
    public User getUserByUserId(Long userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(
                selectUserByUserIdSql, namedParameters, userRowMapper
        );
    }

    @Override
    public User getUserByLogin(String userName) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_name", userName);
        return namedParameterJdbcTemplate.queryForObject(
                selectByUserLoginSql, namedParameters, userRowMapper
        );
    }

    @Override
    public String getUserPasswordByUserName(String userName) {
        return jdbcTemplate.queryForObject(selectUserPasswordByUserNameSql, new String[]{userName}, String.class);
    }

    @Override
    public Long addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        parameterSource.addValue(USER_NAME, user.getLogin());
        parameterSource.addValue(USER_PASSWORD, user.getPassword());
        parameterSource.addValue(USER_GENDER, user.getGender());
        parameterSource.addValue(USER_BIRTH_DATE, user.getBirthDate());
        parameterSource.addValue(USER_INFORMATION, user.getInformation());
        namedParameterJdbcTemplate.update(
                insertUserSql, parameterSource, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public int updateUser(User user) {
        Map<String, Object> params = new HashMap<>();
//        params.put(USER_ID, user.getUserId());
        params.put(USER_PASSWORD, user.getPassword());
        params.put(USER_GENDER, user.getGender());
        params.put(USER_BIRTH_DATE, user.getBirthDate());
        params.put(USER_INFORMATION, user.getInformation());
        return namedParameterJdbcTemplate.update(updateUserSql, params);
    }

    @Override
    public int deleteUser(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);
        return namedParameterJdbcTemplate.update(deleteUserSql, params);
    }

    @Override
    public boolean checkUserByLogin(String userName) {
        return jdbcTemplate.queryForObject(checkUserByLoginSql, new String[]{userName}, Boolean.class);
    }

    @Override
    public boolean checkUserByUserId(Long userId) {
        return jdbcTemplate.queryForObject(checkUserByUserIdSql, new Long[]{userId}, Boolean.class);
    }

    @Override
    public Long getUserIdByLogin(String login) {
        return jdbcTemplate.queryForObject(getUserIdByLoginSql, new String[]{login}, Long.class);
    }

}
