package com.exchange.dao.jdbc.mapper.dto;

import com.exchange.dto.security.UserDetailsDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User details dto row mapper.
 */
@Component
public class UserDetailsDtoRowMapper implements RowMapper<UserDetailsDto> {

    @Override
    public UserDetailsDto mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserId(rs.getLong("id"));
        userDetailsDto.setUserName(rs.getString("user_name"));
        userDetailsDto.setUserPassword(rs.getString("user_password"));
        Set<String> set = new HashSet<>();
        do {
            set.add(rs.getString("role"));
        } while (rs.next());
        userDetailsDto.setRoles(set);
        return userDetailsDto;
    }

}
