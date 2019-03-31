package com.exchange.dao.jdbc;

import com.exchange.dao.RoleDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Role dao.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    private static final String USER_ID = "user_id";
    private static final String ROLE_ID = "role_id";
    @Value("${userRole.insertRole}")
    private String insertRoleSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Instantiates a new Role dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     */
    public RoleDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Integer addUserRole(final Long userId, final Integer roleId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(ROLE_ID, roleId);
        return namedParameterJdbcTemplate.update(insertRoleSql, parameterSource);
    }

}
