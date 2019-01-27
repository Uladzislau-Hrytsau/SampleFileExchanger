package com.exchange.dao.jdbc.mapper;

import com.exchange.dao.UserRole;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type User role row mapper.
 */
@Component
public class UserRoleRowMapper implements RowMapper<UserRole> {

    @Override
    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserRole(
                rs.getLong("user_id"),
                rs.getLong("user_role")
        );
    }

}
