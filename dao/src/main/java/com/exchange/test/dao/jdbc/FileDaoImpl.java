package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.File;
import com.exchange.test.dao.FileDao;
import com.exchange.test.dao.jdbc.mapper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * FileDao implementation.
 */
public class FileDaoImpl implements FileDao {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String URL = "url";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String CATEGORY = "category";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${file.selectByUserIdAndCategory}")
    String getAllFilesByUserIdAndCategorySql;

    @Value("${file.selectByUserIdAndDate}")
    String getAllFilesByUserIdAndDateSql;

    @Value("${file.selectByUserId}")
    String getAllFilesByUserIdSql;

    @Value("${file.select}")
    String getAllFilesSql;

    @Value("${file.selectById}")
    String getFileByIdSql;

    @Value("${file.insert}")
    String addFileSql;

    @Value("${file.update}")
    String updateFileSql;

    @Value("${file.delete}")
    String deleteFileSql;

    public FileDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<File> getAllFilesByUserIdAndCategory(Long userId, String category) throws DataAccessException {
        return null;
    }

    public List<File> getAllFilesByUserIdAndDate(Long userId, Date date) throws DataAccessException {
        return null;
    }

    public List<File> getAllFilesByUserId(Long userId) throws DataAccessException {
        return null;
    }

    public List<File> getAllFiles() throws DataAccessException {
        return jdbcTemplate.query(getAllFilesSql, new FileRowMapper());
    }

    public File getFileById(Long id) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_id", ID);
        File file = namedParameterJdbcTemplate.queryForObject(
                getFileByIdSql, namedParameters, new FileRowMapper());
        return file;
    }

    public Long addFile(File file) throws DataAccessException {
        return null;
    }

    public int updateFile(File file) throws DataAccessException {
        return 0;
    }

    public int deleteFile(Long id) throws DataAccessException {
        return 0;
    }
}
