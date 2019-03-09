package com.exchange.dao.jdbc;

import com.exchange.dao.Category;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.jdbc.mapper.model.CategoryRowMapper;
import com.exchange.dto.FileCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

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
    @Value("${category.insertFileCategories}")
    private String insertFileCategoriesSql;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CategoryRowMapper categoryRowMapper;

    /**
     * Instantiates a new Category dao.
     *
     * @param jdbcTemplate               the jdbc template
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param categoryRowMapper          the category row mapper
     */
    @Autowired
    public CategoryDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, CategoryRowMapper categoryRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
    public Boolean existsByCategories(Set<Long> categories, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(CATEGORY_ID, categories);
        return categories.size() == namedParameterJdbcTemplate.queryForObject(existsByUserIdAndCategoryIdSql, parameterSource, Integer.class);
    }

    @Override
    public int[] addFileCategories(Set<FileCategoryDto> categories) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(insertFileCategoriesSql, batch);
    }
}
