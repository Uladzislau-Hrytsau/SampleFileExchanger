package com.exchange.dao.jdbc;

import com.exchange.dao.Category;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.jdbc.mapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

/**
 * CategoryDao implementation.
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public class CategoryDaoImpl implements CategoryDao {

    public static final String ID = "id";
    public static final String CATEGORY = "category";
    @Value("${category.select}")
    String getAllCategoriesSql;
    @Value("${category.selectById}")
    String getCategoryByIdSql;
    @Value("${category.checkCategoryById}")
    String checkCategoryByIdSql;
    private CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategoryDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(getAllCategoriesSql, categoryRowMapper);
    }

    @Override
    public Category getCategoryById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(getCategoryByIdSql, parameterSource, categoryRowMapper);
    }

    @Override
    public boolean checkCategoryById(Long id) {
        return jdbcTemplate.queryForObject(checkCategoryByIdSql, new Object[]{id}, boolean.class);
    }
}
