package com.exchange.dao.jdbc.mapper.dto;

import com.exchange.dto.category.CategoryDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.CategoryDaoImpl.CATEGORY;
import static com.exchange.dao.jdbc.CategoryDaoImpl.ID;

/**
 * The type Category dto row mapper.
 */
@Component
public class CategoryDtoRowMapper implements RowMapper<CategoryDto> {

    @Override
    public CategoryDto mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(rs.getLong(ID));
        categoryDto.setName(rs.getString(CATEGORY));
        return categoryDto;
    }
}
