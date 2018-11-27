package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.jdbc.mapper.*;
import com.exchange.test.dao.User;
import com.exchange.test.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

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

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.select}")
    private String getAllUsersSql;

    @Value("${user.selectById}")
    private String selectUserBypIdSql;

    @Value("${user.selectByLogin}")
    private String selectUserByLoginSql;

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
        return jdbcTemplate.query(getAllUsersSql, new UserRowMapper());
    }

    public User getUserById(Integer userId) throws DataAccessException {
        return null;
    }

    public User getUserByLogin(String login) throws DataAccessException {
        return null;
    }

    public Integer addUser(User user) throws DataAccessException {
        return null;
    }

    public int updateUser(User user) throws DataAccessException {
        return 0;
    }

    public int deleteUser(Integer userId) throws DataAccessException {
        return 0;
    }

}
