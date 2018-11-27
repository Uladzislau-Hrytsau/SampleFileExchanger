package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.CategoryDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * CategoryDao implementation.
 */
public class CategoryDaoImpl implements CategoryDao {

    public static final String ID = "id";
    public static final String CATEGORY = "category";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Value()

    public CategoryDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }



}
