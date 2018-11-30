package com.exchange.test.dao.jdbc.mapper;


import com.exchange.test.dao.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.exchange.test.dao.jdbc.UserDaoImpl.*;

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