package com.exchange.dao.jdbc;

import com.exchange.dao.FolderDao;
import com.exchange.dao.jdbc.mapper.dto.FolderStructureDtoRowMapper;
import com.exchange.dto.folder.FolderStructureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Folder dao.
 */
@Component
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
    public FolderDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, FolderStructureDtoRowMapper folderStructureDtoRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.folderStructureDtoRowMapper = folderStructureDtoRowMapper;
    }

    @Override
    public List<FolderStructureDto> getFoldersByUserIdAndParentId(Long userId, Long parentId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(PARENT_ID, parentId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(selectByUserIdAndParentIdSql, parameterSource, folderStructureDtoRowMapper);
    }

    @Override
    public Integer addFolder(FolderStructureDto folderStructureDto, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(NAME, folderStructureDto.getName());
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(PARENT_ID, folderStructureDto.getId());
        return namedParameterJdbcTemplate.update(insertSql, parameterSource);
    }

    @Override
    public Boolean existsParentIdByUserId(Long parentId, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, userId);
        parameterSource.addValue(ID, parentId);
        return namedParameterJdbcTemplate.queryForObject(existsIdByUserIdSql, parameterSource, Boolean.class);
    }

    @Override
    public Integer deleteByFolderIdAndUserId(Long folderId, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        this.fillFolderIdAndUserId(parameterSource, folderId, userId);
        return namedParameterJdbcTemplate.update(deleteByUserIdAndFolderIdSql, parameterSource);
    }

    @Override
    public Integer updateFolderNameByFolderIdAndUserId(FolderStructureDto folderStructureDto, Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(NAME, folderStructureDto.getName());
        this.fillFolderIdAndUserId(parameterSource, folderStructureDto.getId(), userId);
        return namedParameterJdbcTemplate.update(updateFolderNameByFolderIdAndUserIdSql, parameterSource);
    }

    private void fillFolderIdAndUserId(MapSqlParameterSource parameterSource, Long id, Long userId) {
        parameterSource.addValue(ID, id);
        parameterSource.addValue(USER_ID, userId);
    }

}
