package com.exchange.dao.jdbc.mapper.dto;

import com.exchange.dto.FolderStructureDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.FolderDaoImpl.ID;
import static com.exchange.dao.jdbc.FolderDaoImpl.NAME;

/**
 * The type Folder structure dto row mapper.
 */
@Component
public class FolderStructureDtoRowMapper implements RowMapper<FolderStructureDto> {

    @Override
    public FolderStructureDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        FolderStructureDto folderStructureDto = new FolderStructureDto();
        folderStructureDto.setId(rs.getLong(ID));
        folderStructureDto.setName(rs.getString(NAME));
        return folderStructureDto;
    }
}
