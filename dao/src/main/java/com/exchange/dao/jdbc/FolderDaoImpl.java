package com.exchange.dao.jdbc;

import com.exchange.dao.FolderDao;
import com.exchange.dao.jdbc.mapper.dto.FolderStructureDtoRowMapper;
import com.exchange.dto.FolderStructureDto;
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

    @Value("${folder.selectByUserIdAndParentId}")
    private String selectByUserIdAndParentIdSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private FolderStructureDtoRowMapper folderStructureDtoRowMapper;

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
    public List<FolderStructureDto> getAllFoldersByUserIdAndParentId(Long userId, Long parentId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(PARENT_ID, parentId);
        parameterSource.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(selectByUserIdAndParentIdSql, parameterSource, folderStructureDtoRowMapper);
    }

}
