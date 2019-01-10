package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.jdbc.mapper.FileRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileDao implementation.
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public class FileDaoImpl implements FileDao {

    /**
     * The constant ID.
     */
    public static final String ID = "id";
    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "user_id";
    /**
     * The constant URL.
     */
    public static final String URL = "url";
    /**
     * The constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";
    /**
     * The constant DATE.
     */
    public static final String DATE = "date";
    /**
     * The constant CATEGORY.
     */
    public static final String CATEGORY = "category";

    /**
     * The Get all files by user id and category sql.
     */
    @Value("${file.selectByUserIdAndCategory}")
    private String getAllFilesByUserIdAndCategorySql;
    /**
     * The Get all files by user id and date sql.
     */
    @Value("${file.selectByUserIdAndDate}")
    private String getAllFilesByUserIdAndDateSql;
    /**
     * The Get all files by user id sql.
     */
    @Value("${file.selectByUserId}")
    private String getAllFilesByUserIdSql;
    /**
     * The Get all files sql.
     */
    @Value("${file.select}")
    private String getAllFilesSql;
    /**
     * The Get file by id sql.
     */
    @Value("${file.selectById}")
    private String getFileByIdSql;
    /**
     * The Add file sql.
     */
    @Value("${file.insert}")
    private String addFileSql;
    /**
     * The Update file sql.
     */
    @Value("${file.update}")
    private String updateFileSql;
    /**
     * The Delete file sql.
     */
    @Value("${file.delete}")
    private String deleteFileSql;
    /**
     * The Check file by id sql.
     */
    @Value("${file.checkFileById}")
    private String checkFileByIdSql;
    /**
     * The Check file by user id sql.
     */
    @Value("${file.checkFileByUserId}")
    private String checkFileByUserIdSql;
    /**
     * The Check file by url sql.
     */
    @Value("${file.checkFileByUrl}")
    private String checkFileByUrlSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private FileRowMapper fileRowMapper = new FileRowMapper();


    /**
     * Instantiates a new File dao.
     *
     * @param dataSource the data source
     */
    public FileDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<File> getAllFilesByUserId(Long userId) throws DataAccessException {
        return jdbcTemplate.query(getAllFilesByUserIdSql, fileRowMapper, userId);
    }

    @Override
    public List<File> getAllFiles() throws DataAccessException {
        return jdbcTemplate.query(getAllFilesSql, fileRowMapper);
    }

    @Override
    public File getFileById(Long id) throws DataAccessException {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                getFileByIdSql, namedParameters, fileRowMapper);
    }

    @Override
    public Long addFile(File file) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, file.getId());
        parameterSource.addValue(USER_ID, file.getUser_id());
        parameterSource.addValue(URL, file.getUrl());
        parameterSource.addValue(DESCRIPTION, file.getDescription());
        parameterSource.addValue(DATE, file.getDate());
        parameterSource.addValue(CATEGORY, file.getCategoryId());
        namedParameterJdbcTemplate.update(
                addFileSql, parameterSource, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public int updateFile(File file) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ID, file.getId());
        params.put(USER_ID, file.getUser_id());
        params.put(URL, file.getUrl());
        params.put(DESCRIPTION, file.getDescription());
        params.put(DATE, file.getDate());
        params.put(CATEGORY, file.getCategoryId());
        return namedParameterJdbcTemplate.update(updateFileSql, params);
    }

    @Override
    public int deleteFile(Long id) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ID, id);
        return namedParameterJdbcTemplate.update(deleteFileSql, params);
    }

    @Override
    public boolean checkFileById(Long id) {
        return jdbcTemplate.queryForObject(checkFileByIdSql, new Object[]{id}, boolean.class);
    }

    @Override
    public boolean checkFileByUserId(Long userId) {
        return jdbcTemplate.queryForObject(checkFileByUserIdSql, new Object[]{userId}, boolean.class);
    }

    @Override
    public boolean checkFileByUrl(String url) {
        return jdbcTemplate.queryForObject(checkFileByUrlSql, new Object[]{url}, boolean.class);
    }
}
