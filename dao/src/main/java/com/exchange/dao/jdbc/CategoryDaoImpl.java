package com.exchange.dao.jdbc;

import com.exchange.dao.Category;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.jdbc.mapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    public static final String ID = "id";
    public static final String CATEGORY = "category";

    @Value("${category.select}")
    private String getAllCategoriesSql;
    @Value("${category.selectById}")
    private String getCategoryByIdSql;
    @Value("${category.checkCategoryById}")
    private String checkCategoryByIdSql;

    private CategoryRowMapper categoryRowMapper;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
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
}
