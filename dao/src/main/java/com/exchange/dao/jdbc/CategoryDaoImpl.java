package com.exchange.dao.jdbc;

import com.exchange.dao.CategoryDao;
import com.exchange.dto.file.FileCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Instantiates a new Category dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     */
    @Autowired
    public CategoryDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
