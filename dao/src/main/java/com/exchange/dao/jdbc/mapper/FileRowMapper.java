package com.exchange.dao.jdbc.mapper;

import com.exchange.dao.File;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.exchange.dao.jdbc.FileDaoImpl.*;

/**
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
@Component
public class FileRowMapper implements RowMapper<File> {
    public File mapRow(ResultSet rs, int rowNum) throws SQLException {
        File file = new File(
                rs.getLong(ID),
                rs.getLong(USER_ID),
                rs.getString(URL),
                rs.getString(DESCRIPTION),
                rs.getLong(CATEGORY)
        );
        Date date = rs.getDate(DATE);
        if (date != null) {
            file.setDate(date.toLocalDate());
        }
        return file;
    }
}
