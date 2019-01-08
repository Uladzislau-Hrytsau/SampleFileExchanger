package com.exchange.dao.jdbc.mapper;

import com.exchange.dao.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.CategoryDaoImpl.CATEGORY;
import static com.exchange.dao.jdbc.CategoryDaoImpl.ID;

/**
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public class CategoryRowMapper implements RowMapper<Category> {

    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(
                rs.getLong(ID),
                rs.getString(CATEGORY)
        );
    }
}