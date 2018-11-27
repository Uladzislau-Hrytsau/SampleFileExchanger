package com.exchange.test.dao.jdbc.mapper;


import com.exchange.test.dao.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.test.dao.jdbc.UserDaoImpl.*;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong(USER_ID),
                rs.getString(USER_NAME),
                rs.getString(USER_PASSWORD),
                rs.getBoolean(USER_GENDER),
                rs.getDate(USER_BIRTH_DATE),
                rs.getString(USER_INFORMATION)
        );
//            user.setUserId(rs.getLong(USER_ID));
//            user.setLogin(rs.getString(USER_NAME));
//            user.setPassword(rs.getString(USER_PASSWORD));
//            user.setGender(rs.getBoolean(USER_GENDER));
//            user.setBirthDate(rs.getDate(USER_BIRTH_DATE));
//            user.setInformation(rs.getString(USER_INFORMATION));
        return user;
    }
}