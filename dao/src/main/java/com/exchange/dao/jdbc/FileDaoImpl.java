package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.jdbc.mapper.dto.FileStructureDtoRowMapper;
import com.exchange.dao.jdbc.mapper.model.FileRowMapper;
import com.exchange.dto.FileStructureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type File dao.
 */
@Component
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
     * The constant DATE.
     */
    public static final String DATE = "date";
    /**
     * The constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";
    /**
     * The constant FOLDER_ID.
     */
    public static final String FOLDER_ID = "folder_id";
    /**
     * The constant REAL_NAME.
     */
    public static final String REAL_NAME = "real_name";
    /**
     * The constant ENCODE_NAME.
     */
    public static final String ENCODE_NAME = "encode_name";

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
    @Value("${file.existsByEncodeName}")
    private String existsByEncodeNameSql;
    @Value("${file.selectByUserIdAndFolderId}")
    private String selectByUserIdAndFolderIdSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private FileRowMapper fileRowMapper;
    private FileStructureDtoRowMapper fileStructureDtoRowMapper;

    /**
     * Instantiates a new File dao.
     *
     * @param jdbcTemplate               the jdbc template
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param fileRowMapper              the file row mapper
     * @param fileStructureDtoRowMapper  the file structure dto row mapper
     */
    @Autowired
    public FileDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, FileRowMapper fileRowMapper, FileStructureDtoRowMapper fileStructureDtoRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.fileRowMapper = fileRowMapper;
        this.fileStructureDtoRowMapper = fileStructureDtoRowMapper;
    }


    @Override
    public List<File> getAllFilesByUserId(Long userId) {
        return jdbcTemplate.query(getAllFilesByUserIdSql, fileRowMapper, userId);
    }

    @Override
    public List<File> getAllFiles() {
        return jdbcTemplate.query(getAllFilesSql, fileRowMapper);
    }

    @Override
    public File getFileById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                getFileByIdSql, namedParameters, fileRowMapper);
    }

    @Override
    public Long addFile(File file) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, file.getId());
        parameterSource.addValue(USER_ID, file.getUserId());
        parameterSource.addValue(FOLDER_ID, file.getFolderId());
        parameterSource.addValue(DESCRIPTION, file.getDescription());
        parameterSource.addValue(REAL_NAME, file.getRealName());
        parameterSource.addValue(ENCODE_NAME, file.getEncodeName());
        parameterSource.addValue(DATE, file.getDate());
        namedParameterJdbcTemplate.update(addFileSql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int updateFile(File file) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, file.getId());
        params.put(USER_ID, file.getUserId());
//        params.put(URL, file.getUrl());
        params.put(DESCRIPTION, file.getDescription());
        params.put(DATE, file.getDate());
//        params.put(CATEGORY, file.getCategoryId());
        return namedParameterJdbcTemplate.update(updateFileSql, params);
    }

    @Override
    public int deleteFile(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        return namedParameterJdbcTemplate.update(deleteFileSql, params);
    }

    @Override
    public boolean checkFileById(Long id) {
        return jdbcTemplate.queryForObject(checkFileByIdSql, new Long[]{id}, boolean.class);
    }

    @Override
    public boolean checkFileByUserId(Long userId) {
        return jdbcTemplate.queryForObject(checkFileByUserIdSql, new Long[]{userId}, boolean.class);
    }

    @Override
    public boolean checkFileByUrl(String url) {
        return jdbcTemplate.queryForObject(checkFileByUrlSql, new String[]{url}, boolean.class);
    }

    @Override
    public boolean existsByEncodeName(String encodeName) {
        return jdbcTemplate.queryForObject(existsByEncodeNameSql, new String[]{encodeName}, boolean.class);
    }

    @Override
    public List<FileStructureDto> getAllFilesByUserIdAndFolderId(Long userId, Long folderId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(FOLDER_ID, folderId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(selectByUserIdAndFolderIdSql, parameterSource, fileStructureDtoRowMapper);
    }
}
