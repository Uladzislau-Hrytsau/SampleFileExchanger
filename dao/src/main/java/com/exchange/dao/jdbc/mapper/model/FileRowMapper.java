package com.exchange.dao.jdbc.mapper.model;

import com.exchange.dao.File;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.FileDaoImpl.*;

/**
 * The type File row mapper.
 */
@Component
public class FileRowMapper implements RowMapper<File> {
    public File mapRow(ResultSet rs, int rowNum) throws SQLException {
        File file = new File();
        file.setId(rs.getLong(ID));
        file.setUserId(rs.getLong(USER_ID));
        file.setDate(rs.getDate(DATE).toLocalDate());
        file.setDescription(rs.getString(DESCRIPTION));
        file.setFolderId(rs.getLong(FOLDER_ID));
        file.setRealName(rs.getString(REAL_NAME));
        file.setEncodeName(rs.getString(ENCODE_NAME));
        return file;
    }
}
