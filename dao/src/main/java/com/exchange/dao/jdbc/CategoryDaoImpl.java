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
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * The type Category dao.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

    public static final String ID = "id";
    public static final String CATEGORY = "category";
    private static final String USER_ID = "user_id";
    private static final String CATEGORY_ID = "category_id";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CategoryDtoRowMapper categoryDtoRowMapper;
    @Value("${category.existsByUserIdAndCategoryId}")
    private String existsByUserIdAndCategoryIdSql;
    @Value("${category.insertFileCategories}")
    private String insertFileCategoriesSql;
    @Value("${category.getCategoriesByUserId}")
    private String getCategoriesByUserIdSql;

    /**
     * Instantiates a new Category dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param categoryDtoRowMapper       the category dto row mapper
     */
    @Autowired
    public CategoryDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate, final CategoryDtoRowMapper categoryDtoRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.categoryDtoRowMapper = categoryDtoRowMapper;
    }

    @Override
    public Boolean existsCategoriesByUserId(final Set<Long> categories, final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(CATEGORY_ID, categories);
        return categories.size() == namedParameterJdbcTemplate.queryForObject(existsByUserIdAndCategoryIdSql, parameterSource, Integer.class);
    }

    @Override
    public int[] addFileCategories(final Set<FileCategoryDto> fileCategoryDtos) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(fileCategoryDtos);
        return namedParameterJdbcTemplate.batchUpdate(insertFileCategoriesSql, batch);
    }

    @Override
    public List<CategoryDto> getCategoriesByUserId(final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(getCategoriesByUserIdSql, parameterSource, categoryDtoRowMapper);
    }

}
