package com.exchange.dao.jdbc;

import com.exchange.dao.Category;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.jdbc.mapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

/**
 * The type Category dao.
 */
@Component
public class CategoryDaoImpl implements CategoryDao {

    private static final String USER_ID = "user_id";
    private static final String CATEGORY_ID = "category_id";

    @Value("${category.select}")
    private String getAllCategoriesSql;
    @Value("${category.selectById}")
    private String getCategoryByIdSql;
    @Value("${category.checkCategoryById}")
    private String checkCategoryByIdSql;
    @Value("${category.existsByUserIdAndCategoryId}")
    private String existsByUserIdAndCategoryIdSql;

    private JdbcTemplate jdbcTemplate;
    private CategoryRowMapper categoryRowMapper;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Instantiates a new Category dao.
     *
     * @param dataSource        the data source
     * @param categoryRowMapper the category row mapper
     */
    public CategoryDaoImpl(DataSource dataSource, CategoryRowMapper categoryRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.categoryRowMapper = categoryRowMapper;
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

    @Override
    public Boolean existsByCategories(Set<Category> categories) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        categories.forEach(
                category -> {
                    parameterSource.addValue(USER_ID, category.getUserId());
                    parameterSource.addValue(CATEGORY_ID, category.getCategoryId());
                }
        );
        return namedParameterJdbcTemplate.queryForObject(existsByUserIdAndCategoryIdSql, parameterSource, Boolean.class);
    }
}
