package com.exchange.dao.jdbc.mapper.dto;

import com.exchange.dto.file.FileStructureDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.FileDaoImpl.*;

/**
 * The type File structure dto row mapper.
 */
@Component
public class FileStructureDtoRowMapper implements RowMapper<FileStructureDto> {

    @Override
    public FileStructureDto mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        FileStructureDto fileStructureDto = new FileStructureDto();
        fileStructureDto.setId(rs.getLong(ID));
        fileStructureDto.setFolderId(rs.getLong(FOLDER_ID));
        fileStructureDto.setName(rs.getString(REAL_NAME));
        return fileStructureDto;
    }
}
