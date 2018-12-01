package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.CategoryDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * CategoryDao implementation.
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public class CategoryDaoImpl implements CategoryDao {

    public static final String ID = "id";
    public static final String CATEGORY = "category";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategoryDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


}
