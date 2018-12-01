package com.exchange.test.dao.jdbc;

import com.exchange.test.dao.File;
import com.exchange.test.dao.FileDao;
import com.exchange.test.dao.jdbc.mapper.FileRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileDao implementation.
 * Created by Uladzislau Hrytsau on 27.11.18.
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

    private FileRowMapper fileRowMapper = new FileRowMapper();

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

    public List<File> getAllFilesByUserIdAndCategory(Long userId, String category) throws NotImplementedException {
        return null;
    }

    public List<File> getAllFilesByUserIdAndDate(Long userId, LocalDate date) throws NotImplementedException {
        return null;
    }

    public List<File> getAllFilesByUserId(Long userId) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
//        return jdbcTemplate.queryForObject(getAllFilesByUserIdSql, namedParameters, new FileRowMapper());
        return jdbcTemplate.query(getAllFilesByUserIdSql, fileRowMapper, userId);
    }

    public List<File> getAllFiles() throws DataAccessException {
        return jdbcTemplate.query(getAllFilesSql, fileRowMapper);
    }

    public File getFileById(Long id) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_id", id);
        File file = namedParameterJdbcTemplate.queryForObject(
                getFileByIdSql, namedParameters, fileRowMapper);
        return file;
    }

    public Long addFile(File file) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, file.getId());
        parameterSource.addValue(USER_ID, file.getUser_id());
        parameterSource.addValue(URL, file.getUrl());
        parameterSource.addValue(DESCRIPTION, file.getDescription());
        parameterSource.addValue(DATE, file.getDate());
        parameterSource.addValue(CATEGORY, file.getCategory());
        namedParameterJdbcTemplate.update(
                addFileSql, parameterSource, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public int updateFile(File file) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ID, file.getId());
        params.put(USER_ID, file.getUser_id());
        params.put(URL, file.getUrl());
        params.put(DESCRIPTION, file.getDescription());
        params.put(DATE, file.getDate());
        params.put(CATEGORY, file.getCategory());
        return namedParameterJdbcTemplate.update(updateFileSql, params);
    }

    public int deleteFile(Long id) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ID, id);
        return namedParameterJdbcTemplate.update(deleteFileSql, params);
    }
}
