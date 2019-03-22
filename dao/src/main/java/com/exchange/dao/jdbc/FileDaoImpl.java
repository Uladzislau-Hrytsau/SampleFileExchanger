package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.jdbc.mapper.dto.FileStructureDtoRowMapper;
import com.exchange.dao.jdbc.mapper.model.FileRowMapper;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.file.FileUpdatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

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
    /**
     * The constant LIMIT.
     */
    public static final String LIMIT = "limit";
    /**
     * The constant OFFSET.
     */
    public static final String OFFSET = "offset";

    @Value("${file.insert}")
    private String addFileSql;
    @Value("${file.update}")
    private String updateFileSql;
    @Value("${file.delete}")
    private String deleteFileSql;
    @Value("${file.selectByUserIdAndFolderId}")
    private String selectByUserIdAndFolderIdSql;
    @Value("${file.selectByLimitAndOffset}")
    private String selectByLimitAndOffsetSql;
    @Value("${file.selectFilesCount}")
    private String selectFilesCountSql;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final FileRowMapper fileRowMapper;
    private final FileStructureDtoRowMapper fileStructureDtoRowMapper;

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
    public List<File> getFilesByLimitAndOffset(Integer limit, Integer offset) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(LIMIT, limit);
        parameterSource.addValue(OFFSET, offset);
        return namedParameterJdbcTemplate.query(selectByLimitAndOffsetSql, parameterSource, fileRowMapper);
    }

    @Override
    public Long addFile(FileDto fileDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, fileDto.getUserId());
        parameterSource.addValue(FOLDER_ID, fileDto.getFolderId());
        parameterSource.addValue(DESCRIPTION, fileDto.getDescription());
        parameterSource.addValue(REAL_NAME, fileDto.getRealName());
        parameterSource.addValue(ENCODE_NAME, fileDto.getEncodeName());
        parameterSource.addValue(DATE, fileDto.getDate());
        namedParameterJdbcTemplate.update(addFileSql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Integer updateFile(FileUpdatingDto fileUpdatingDto) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, fileUpdatingDto.getId());
        parameterSource.addValue(DESCRIPTION, fileUpdatingDto.getDescription());
        parameterSource.addValue(REAL_NAME, fileUpdatingDto.getRealName());
        parameterSource.addValue(DATE, fileUpdatingDto.getDate());
        return namedParameterJdbcTemplate.update(updateFileSql, parameterSource);
    }

    @Override
    public Integer deleteFile(Long fileId, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, fileId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.update(deleteFileSql, parameterSource);
    }

    @Override
    public List<FileStructureDto> getFilesByUserIdAndFolderId(Long userId, Long folderId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(FOLDER_ID, folderId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(selectByUserIdAndFolderIdSql, parameterSource, fileStructureDtoRowMapper);
    }

    @Override
    public Long getFileCount() {
        return jdbcTemplate.queryForObject(selectFilesCountSql, Long.class);
    }
}
