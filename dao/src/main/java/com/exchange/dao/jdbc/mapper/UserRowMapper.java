package com.exchange.dao.jdbc.mapper;


import com.exchange.dao.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.UserDaoImpl.*;

/**
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong(USER_ID),
                rs.getString(USER_NAME),
                rs.getString(USER_PASSWORD),
                rs.getBoolean(USER_GENDER),
                rs.getString(USER_INFORMATION)
        );
        Date date = rs.getDate(USER_BIRTH_DATE);
        if (date != null) {
            user.setBirthDate(date.toLocalDate());
        }
        return user;
    }
}