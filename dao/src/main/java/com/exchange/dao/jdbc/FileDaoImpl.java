package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.jdbc.mapper.dto.FileStructureDtoRowMapper;
import com.exchange.dao.jdbc.mapper.dto.FileUpdatingDtoRowMapper;
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
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type File dao.
 */
@Repository
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
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final FileRowMapper fileRowMapper;
    private final FileStructureDtoRowMapper fileStructureDtoRowMapper;
    private final FileUpdatingDtoRowMapper fileUpdatingDtoRowMapper;
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
    @Value("${file.getFileNameByFileId}")
    private String getFileNameByFileIdSql;
    @Value("${file.getFileNamesByFolderIdAndUserId}")
    private String getFileNamesByFolderIdAndUserIdSql;
    @Value("${file.getFileByFileIdAndUserId}")
    private String getGetFileNameByFileIdAndUserIdSql;
    @Value("${file.fetFileNamesByUserId}")
    private String fetFileNamesByUserIdSql;

    /**
     * Instantiates a new File dao.
     *
     * @param jdbcTemplate               the jdbc template
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param fileRowMapper              the file row mapper
     * @param fileStructureDtoRowMapper  the file structure dto row mapper
     * @param fileUpdatingDtoRowMapper   the file updating dto row mapper
     */
    @Autowired
    public FileDaoImpl(
            final JdbcTemplate jdbcTemplate,
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            final FileRowMapper fileRowMapper,
            final FileStructureDtoRowMapper fileStructureDtoRowMapper,
            final FileUpdatingDtoRowMapper fileUpdatingDtoRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.fileRowMapper = fileRowMapper;
        this.fileStructureDtoRowMapper = fileStructureDtoRowMapper;
        this.fileUpdatingDtoRowMapper = fileUpdatingDtoRowMapper;
    }

    @Override
    public List<File> getFilesByLimitAndOffset(final Integer limit, final Integer offset) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(LIMIT, limit);
        parameterSource.addValue(OFFSET, offset);
        return namedParameterJdbcTemplate.query(selectByLimitAndOffsetSql, parameterSource, fileRowMapper);
    }

    @Override
    public Long addFile(final FileDto fileDto) {
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
    public Boolean updateFile(final FileUpdatingDto fileUpdatingDto) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, fileUpdatingDto.getId());
        parameterSource.addValue(DESCRIPTION, fileUpdatingDto.getDescription());
        parameterSource.addValue(REAL_NAME, fileUpdatingDto.getRealName());
        parameterSource.addValue(DATE, fileUpdatingDto.getDate());
        return namedParameterJdbcTemplate.update(updateFileSql, parameterSource) == 1;
    }

    @Override
    public Boolean deleteFile(final Long fileId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, fileId);
        return namedParameterJdbcTemplate.update(deleteFileSql, parameterSource) == 1;
    }

    @Override
    public List<FileStructureDto> getFilesByUserIdAndFolderId(final Long userId, final Long folderId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(FOLDER_ID, folderId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(selectByUserIdAndFolderIdSql, parameterSource, fileStructureDtoRowMapper);
    }

    @Override
    public Long getFileCount() {
        return jdbcTemplate.queryForObject(selectFilesCountSql, Long.class);
    }

    @Override
    public String getFileNameByFileId(final Long fileId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, fileId);
        return namedParameterJdbcTemplate.queryForObject(getFileNameByFileIdSql, parameterSource, String.class);
    }

    @Override
    public List<String> getFileNamesByFolderIdAndUserId(final Long folderId, final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(ID, folderId);
        return namedParameterJdbcTemplate.queryForList(getFileNamesByFolderIdAndUserIdSql, parameterSource, String.class);
    }

    @Override
    public FileUpdatingDto getFileInformationByFileIdAndUserId(final Long fileId, final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, fileId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.queryForObject(getGetFileNameByFileIdAndUserIdSql, parameterSource, fileUpdatingDtoRowMapper);
    }

    @Override
    public List<String> getFileNamesByUserId(final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.queryForList(fetFileNamesByUserIdSql, parameterSource, String.class);
    }
}
