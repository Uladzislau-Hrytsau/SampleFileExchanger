package com.exchange.dao.jdbc.mapper.dto;

import com.exchange.dto.category.CategoryDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Category dto row mapper.
 */
@Component
public class CategoryDtoRowMapper implements RowMapper<CategoryDto> {

    private static final String ID = "id";
    private static final String CATEGORY = "category";

    @Override
    public CategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(rs.getLong(ID));
        categoryDto.setName(rs.getString(CATEGORY));
        return categoryDto;
    }
}
