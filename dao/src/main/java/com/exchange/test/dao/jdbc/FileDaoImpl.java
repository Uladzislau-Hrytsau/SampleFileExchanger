package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.FileDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * FileDao implementation.
 */
public class FileDaoImpl implements FileDao {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String URL = "url";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String CATEGORY = "category";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Value()

    public FileDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

}
