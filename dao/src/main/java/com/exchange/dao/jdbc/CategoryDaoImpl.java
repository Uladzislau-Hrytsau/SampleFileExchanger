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

    /**
     * The constant ID.
     */
    public static final String ID = "id";
    /**
     * The constant CATEGORY.
     */
    public static final String CATEGORY = "category";

    /**
     * The Get all categories sql.
     */
    @Value("${category.select}")
    String getAllCategoriesSql;
    /**
     * The Get category by id sql.
     */
    @Value("${category.selectById}")
    String getCategoryByIdSql;
    /**
     * The Check category by id sql.
     */
    @Value("${category.checkCategoryById}")
    String checkCategoryByIdSql;

    private CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Instantiates a new Category dao.
     *
     * @param dataSource the data source
     */
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
