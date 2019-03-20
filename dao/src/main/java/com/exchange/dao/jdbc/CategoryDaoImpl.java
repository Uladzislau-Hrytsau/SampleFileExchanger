package com.exchange.dao.jdbc;

import com.exchange.dao.CategoryDao;
import com.exchange.dao.jdbc.mapper.dto.CategoryDtoRowMapper;
import com.exchange.dto.category.CategoryDto;
import com.exchange.dto.file.FileCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${category.existsByUserIdAndCategoryId}")
    private String existsByUserIdAndCategoryIdSql;
    @Value("${category.insertFileCategories}")
    private String insertFileCategoriesSql;
    @Value("${category.getCategoriesByUserId}")
    private String getCategoriesByUserIdSql;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CategoryDtoRowMapper categoryDtoRowMapper;

    /**
     * Instantiates a new Category dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param categoryDtoRowMapper       the category dto row mapper
     */
    @Autowired
    public CategoryDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CategoryDtoRowMapper categoryDtoRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.categoryDtoRowMapper = categoryDtoRowMapper;
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

    @Override
    public List<CategoryDto> getCategoriesByUserId(Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(getCategoriesByUserIdSql, parameterSource, categoryDtoRowMapper);
    }

}
