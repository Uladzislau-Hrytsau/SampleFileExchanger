package com.exchange.dao.jdbc.mapper.model;

import com.exchange.dao.File;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.FileDaoImpl.DATE;

/**
 * The type File row mapper.
 */
@Component
public class FileRowMapper implements RowMapper<File> {
    public File mapRow(ResultSet rs, int rowNum) throws SQLException {
        File file = new File(
//                rs.getLong(ID),
//                rs.getLong(USER_ID),
//                rs.getString(URL),
//                rs.getString(DESCRIPTION),
//                rs.getLong(CATEGORY)
        );
        Date date = rs.getDate(DATE);
        if (date != null) {
            file.setDate(date.toLocalDate());
        }
        return file;
    }
}
