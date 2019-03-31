package com.exchange.dao.jdbc;

import com.exchange.dao.FolderDao;
import com.exchange.dao.jdbc.mapper.dto.FolderStructureDtoRowMapper;
import com.exchange.dto.folder.FolderStructureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Folder dao.
 */
@Repository
public class FolderDaoImpl implements FolderDao {

    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "user_id";
    /**
     * The constant ID.
     */
    public static final String ID = "id";
    /**
     * The constant PARENT_ID.
     */
    public static final String PARENT_ID = "parent_id";
    /**
     * The constant NAME.
     */
    public static final String NAME = "name";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final FolderStructureDtoRowMapper folderStructureDtoRowMapper;
    @Value("${folder.selectByUserIdAndParentId}")
    private String selectByUserIdAndParentIdSql;
    @Value("${folder.insert}")
    private String insertSql;
    @Value("${folder.existsByUserId}")
    private String existsIdByUserIdSql;
    @Value("${folder.deleteByUserIdAndFolderId}")
    private String deleteByUserIdAndFolderIdSql;
    @Value("${folder.updateFolderNameByFolderIdAndUserId}")
    private String updateFolderNameByFolderIdAndUserIdSql;

    /**
     * Instantiates a new Folder dao.
     *
     * @param namedParameterJdbcTemplate  the named parameter jdbc template
     * @param folderStructureDtoRowMapper the folder structure dto row mapper
     */
    @Autowired
    public FolderDaoImpl(
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            final FolderStructureDtoRowMapper folderStructureDtoRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.folderStructureDtoRowMapper = folderStructureDtoRowMapper;
    }

    @Override
    public List<FolderStructureDto> getFoldersByUserIdAndParentId(final Long userId, final Long parentId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(PARENT_ID, parentId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(selectByUserIdAndParentIdSql, parameterSource, folderStructureDtoRowMapper);
    }

    @Override
    public Long addFolder(final FolderStructureDto folderStructureDto, final Long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(NAME, folderStructureDto.getName());
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(PARENT_ID, folderStructureDto.getId());
        namedParameterJdbcTemplate.update(insertSql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Boolean existsParentIdByUserId(final Long parentId, final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(ID, parentId);
        return namedParameterJdbcTemplate.queryForObject(existsIdByUserIdSql, parameterSource, Boolean.class);
    }

    @Override
    public Boolean deleteByFolderIdAndUserId(final Long folderId, final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        this.fillFolderIdAndUserId(parameterSource, folderId, userId);
        return namedParameterJdbcTemplate.update(deleteByUserIdAndFolderIdSql, parameterSource) == 1;
    }

    @Override
    public Boolean updateFolderNameByFolderIdAndUserId(final FolderStructureDto folderStructureDto, final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(NAME, folderStructureDto.getName());
        this.fillFolderIdAndUserId(parameterSource, folderStructureDto.getId(), userId);
        return namedParameterJdbcTemplate.update(updateFolderNameByFolderIdAndUserIdSql, parameterSource) == 1;
    }

    private void fillFolderIdAndUserId(final MapSqlParameterSource parameterSource, final Long id, final Long userId) {
        parameterSource.addValue(ID, id);
        parameterSource.addValue(USER_ID, userId);
    }

}
