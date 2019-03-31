package com.exchange.dao.jdbc.mapper.model;


import com.exchange.dao.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.UserDaoImpl.*;

/**
 * The type User row mapper.
 */
@Component
public class UserRowMapper implements RowMapper<User> {

    public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID));
        user.setName(rs.getString(USER_NAME));
        user.setPassword(rs.getString(USER_PASSWORD));
        user.setGender(rs.getBoolean(USER_GENDER));
        user.setInformation(rs.getString(USER_INFORMATION));
        Date date = rs.getDate(USER_BIRTH_DATE);
        if (date != null) {
            user.setBirthDate(date.toLocalDate());
        }
        return user;
    }
}