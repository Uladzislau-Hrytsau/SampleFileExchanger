package com.exchange.test.dao.jdbc.mapper;

import com.exchange.test.dao.File;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.test.dao.jdbc.FileDaoImpl.*;

public class FileRowMapper implements RowMapper<File> {
    public File mapRow(ResultSet rs, int rowNum) throws SQLException {
        File file = new File(
                rs.getLong(ID),
                rs.getLong(USER_ID),
                rs.getString(URL),
                rs.getString(DESCRIPTION),
                rs.getDate(DATE),
                rs.getString(CATEGORY)
        );
        return file;
    }
}
