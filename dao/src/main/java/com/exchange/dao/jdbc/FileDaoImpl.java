package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.jdbc.mapper.FileRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
 * The type File dao.
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

    @Value("${file.selectByUserIdAndCategory}")
    private String getAllFilesByUserIdAndCategorySql;
    @Value("${file.selectByUserIdAndDate}")
    private String getAllFilesByUserIdAndDateSql;
    @Value("${file.selectByUserId}")
    private String getAllFilesByUserIdSql;
    @Value("${file.select}")
    private String getAllFilesSql;
    @Value("${file.selectById}")
    private String getFileByIdSql;
    @Value("${file.insert}")
    private String addFileSql;
    @Value("${file.update}")
    private String updateFileSql;
    @Value("${file.delete}")
    private String deleteFileSql;
    @Value("${file.checkFileById}")
    private String checkFileByIdSql;
    @Value("${file.checkFileByUserId}")
    private String checkFileByUserIdSql;
    @Value("${file.checkFileByUrl}")
    private String checkFileByUrlSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private FileRowMapper fileRowMapper;

    /**
     * Instantiates a new File dao.
     *
     * @param dataSource    the data source
     * @param fileRowMapper the file row mapper
     */
    @Autowired
    public FileDaoImpl(DataSource dataSource, FileRowMapper fileRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.fileRowMapper = fileRowMapper;
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
        Map<String, Object> params = new HashMap<>();
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
        Map<String, Object> params = new HashMap<>();
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
