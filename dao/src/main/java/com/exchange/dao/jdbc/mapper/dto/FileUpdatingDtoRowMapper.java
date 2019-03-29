package com.exchange.dao.jdbc.mapper.dto;

import com.exchange.dto.file.FileUpdatingDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.FileDaoImpl.*;

/**
 * The type File updating dto row mapper.
 */
@Component
public class FileUpdatingDtoRowMapper implements RowMapper<FileUpdatingDto> {

    @Override
    public FileUpdatingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        FileUpdatingDto fileUpdatingDto = new FileUpdatingDto();
        fileUpdatingDto.setId(rs.getLong(ID));
        fileUpdatingDto.setDescription(rs.getString(DESCRIPTION));
        fileUpdatingDto.setRealName(rs.getString(REAL_NAME));
        Date date = rs.getDate(DATE);
        if (date != null) {
            fileUpdatingDto.setDate(date.toLocalDate());
        }
        return fileUpdatingDto;
    }
}
