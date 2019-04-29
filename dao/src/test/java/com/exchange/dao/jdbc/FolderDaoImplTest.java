package com.exchange.dao.jdbc;

import com.exchange.dao.Folder;
import com.exchange.dao.FolderDao;
import com.exchange.dao.TestSpringDaoConfiguration;
import com.exchange.dto.folder.FolderStructureDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Folder dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FolderDaoImplTest {

    private static final Long CORRECT_USER_ID = 1L;
    private static final Long INCORRECT_USER_ID = -1L;
    private static final Long CORRECT_PARENT_ID = 1L;
    private static final Long INCORRECT_PARENT_ID = -1L;
    private static final Integer EXPECTED_COUNT_FOLDER_STRUCTURE_DTOS = 0;
    private static final FolderStructureDto STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO = new FolderStructureDto(
            1L,
            "folder51"
    );
    private static final FolderStructureDto INCORRECT_FOLDER_STRUCTURE_DTO = new FolderStructureDto(
            -1L,
            "folder51"
    );
    private static final FolderStructureDto CORRECT_UPDATED_FOLDER_STRUCTURE_DTO = new FolderStructureDto(
            5L,
            "updatedFolder"
    );

    @Value("${folder.getFolderByFolderId}")
    private String getFolderByFolderIdSql;

    @Autowired
    private FolderDao folderDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets folders by user id and parent id with correct user id and parent id then correct folder structure dtos returned.
     */
    @Test
    public void getFoldersByUserIdAndParentIdWithCorrectUserIdAndParentIdThenCorrectFolderStructureDtosReturned() {
        List<FolderStructureDto> folderStructureDtosBeforeAdding = folderDao.getFoldersByUserIdAndParentId(
                CORRECT_USER_ID, CORRECT_PARENT_ID);
        Long folderId = folderDao.addFolder(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        List<FolderStructureDto> folderStructureDtosAfterAdding = folderDao.getFoldersByUserIdAndParentId(
                CORRECT_USER_ID, CORRECT_PARENT_ID);
        assertNotNull(folderStructureDtosBeforeAdding);
        assertNotNull(folderStructureDtosAfterAdding);
        assertNotNull(folderId);
        Integer countFolderStructureDtosBeforeAdding = folderStructureDtosBeforeAdding.size();
        Integer countFolderStructureDtosAfterAdding = folderStructureDtosAfterAdding.size();
        assertEquals(++countFolderStructureDtosBeforeAdding, countFolderStructureDtosAfterAdding);
    }

    /**
     * Gets folders by user id and parent id with incorrect user id and correct parent id then correct folder structure dtos returned.
     */
    @Test
    public void getFoldersByUserIdAndParentIdWithIncorrectUserIdAndCorrectParentIdThenCorrectFolderStructureDtosReturned() {
        List<FolderStructureDto> folderStructureDtos = folderDao.getFoldersByUserIdAndParentId(
                INCORRECT_USER_ID, CORRECT_PARENT_ID);
        assertNotNull(folderStructureDtos);
        Integer actualCountFolderStructureDtos = folderStructureDtos.size();
        assertEquals(EXPECTED_COUNT_FOLDER_STRUCTURE_DTOS, actualCountFolderStructureDtos);
    }

    /**
     * Gets folders by user id and parent id with correct user id and incorrect parent id then correct folder structure dtos returned.
     */
    @Test
    public void getFoldersByUserIdAndParentIdWithCorrectUserIdAndIncorrectParentIdThenCorrectFolderStructureDtosReturned() {
        List<FolderStructureDto> folderStructureDtos = folderDao.getFoldersByUserIdAndParentId(
                CORRECT_USER_ID, INCORRECT_PARENT_ID);
        assertNotNull(folderStructureDtos);
        Integer actualCountFolderStructureDtos = folderStructureDtos.size();
        assertEquals(EXPECTED_COUNT_FOLDER_STRUCTURE_DTOS, actualCountFolderStructureDtos);
    }

    /**
     * Gets folders by user id and parent id with incorrect user id and parent id then correct folder structure dtos returned.
     */
    @Test
    public void getFoldersByUserIdAndParentIdWithIncorrectUserIdAndParentIdThenCorrectFolderStructureDtosReturned() {
        List<FolderStructureDto> folderStructureDtos = folderDao.getFoldersByUserIdAndParentId(
                INCORRECT_USER_ID, INCORRECT_PARENT_ID);
        assertNotNull(folderStructureDtos);
        Integer actualCountFolderStructureDtos = folderStructureDtos.size();
        assertEquals(EXPECTED_COUNT_FOLDER_STRUCTURE_DTOS, actualCountFolderStructureDtos);
    }

    /**
     * Gets folders by user id and parent id with null user id and parent id then correct folder structure dtos returned.
     */
    @Test
    public void getFoldersByUserIdAndParentIdWithNullUserIdAndParentIdThenCorrectFolderStructureDtosReturned() {
        List<FolderStructureDto> folderStructureDtos = folderDao.getFoldersByUserIdAndParentId(
                null, null);
        assertNotNull(folderStructureDtos);
        Integer actualCountFolderStructureDtos = folderStructureDtos.size();
        assertEquals(EXPECTED_COUNT_FOLDER_STRUCTURE_DTOS, actualCountFolderStructureDtos);
    }

    /**
     * Add folder with correct folder structure dto and user id then correct folder id returned.
     */
    @Test
    public void addFolderWithCorrectFolderStructureDtoAndUserIdThenCorrectFolderIdReturned() {
        Long folderId = folderDao.addFolder(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        assertNotNull(folderId);
        Folder actualFolderByFolderId = this.getFolderByFolderId(folderId);
        assertNotNull(actualFolderByFolderId);
        assertEquals(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO.getId(), actualFolderByFolderId.getParentId());
        assertEquals(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO.getName(), actualFolderByFolderId.getName());
        assertEquals(CORRECT_USER_ID, actualFolderByFolderId.getUserId());
    }

    /**
     * Add folder with incorrect folder structure dto and user id then throw data integrity violation exception.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void addFolderWithIncorrectFolderStructureDtoAndUserIdThenThrowDataIntegrityViolationException() {
        folderDao.addFolder(INCORRECT_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
    }

    /**
     * Add folder with correct folder structure dto and incorrect user id then throw data integrity violation exception.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void addFolderWithCorrectFolderStructureDtoAndIncorrectUserIdThenThrowDataIntegrityViolationException() {
        folderDao.addFolder(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO, INCORRECT_USER_ID);
    }

    /**
     * Add folder with null folder structure dto and user id then throw nll pointer exception.
     */
    @Test(expected = NullPointerException.class)
    public void addFolderWithNullFolderStructureDtoAndUserIdThenThrowNllPointerException() {
        folderDao.addFolder(null, null);
    }

    /**
     * Exists parent id by user id with correct parent id and user id then true returned.
     */
    @Test
    public void existsParentIdByUserIdWithCorrectParentIdAndUserIdThenTrueReturned() {
        Long folderId = folderDao.addFolder(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        assertNotNull(folderId);
        Boolean actualFolderExistingResult = folderDao.existsParentIdByUserId(folderId, CORRECT_USER_ID);
        assertTrue(actualFolderExistingResult);
    }

    /**
     * Exists parent id by user id with incorrect parent id and correct user id then false returned.
     */
    @Test
    public void existsParentIdByUserIdWithIncorrectParentIdAndCorrectUserIdThenFalseReturned() {
        Boolean actualFolderExistingResult = folderDao.existsParentIdByUserId(INCORRECT_PARENT_ID, CORRECT_USER_ID);
        assertFalse(actualFolderExistingResult);
    }

    /**
     * Exists parent id by user id with correct parent id and incorrect user id then false returned.
     */
    @Test
    public void existsParentIdByUserIdWithCorrectParentIdAndIncorrectUserIdThenFalseReturned() {
        Boolean actualFolderExistingResult = folderDao.existsParentIdByUserId(CORRECT_PARENT_ID, INCORRECT_USER_ID);
        assertFalse(actualFolderExistingResult);
    }

    /**
     * Exists parent id by user id with null parent id and user id then false returned.
     */
    @Test
    public void existsParentIdByUserIdWithNullParentIdAndUserIdThenFalseReturned() {
        Boolean actualFolderExistingResult = folderDao.existsParentIdByUserId(null, null);
        assertFalse(actualFolderExistingResult);
    }

    /**
     * Delete by folder id and user id with correct folder id and user id then true returned.
     */
    @Test
    public void deleteByFolderIdAndUserIdWithCorrectFolderIdAndUserIdThenTrueReturned() {
        Boolean actualDeletingResult = folderDao.deleteByFolderIdAndUserId(CORRECT_PARENT_ID, CORRECT_USER_ID);
        assertTrue(actualDeletingResult);
    }

    /**
     * Delete by folder id and user id with incorrect folder id and correct user id then false returned.
     */
    @Test
    public void deleteByFolderIdAndUserIdWithIncorrectFolderIdAndCorrectUserIdThenFalseReturned() {
        Boolean actualDeletingResult = folderDao.deleteByFolderIdAndUserId(INCORRECT_PARENT_ID, CORRECT_USER_ID);
        assertFalse(actualDeletingResult);
    }

    /**
     * Delete by folder id and user id with correct folder id and incorrect user id then false returned.
     */
    @Test
    public void deleteByFolderIdAndUserIdWithCorrectFolderIdAndIncorrectUserIdThenFalseReturned() {
        Boolean actualDeletingResult = folderDao.deleteByFolderIdAndUserId(CORRECT_PARENT_ID, INCORRECT_USER_ID);
        assertFalse(actualDeletingResult);
    }

    /**
     * Delete by folder id and user id with null folder id and user id then false returned.
     */
    @Test
    public void deleteByFolderIdAndUserIdWithNullFolderIdAndUserIdThenFalseReturned() {
        Boolean actualDeletingResult = folderDao.deleteByFolderIdAndUserId(null, null);
        assertFalse(actualDeletingResult);
    }

    /**
     * Update folder name by folder id and user id with correct folder structure dto and user id then true returned.
     */
    @Test
    public void updateFolderNameByFolderIdAndUserIdWithCorrectFolderStructureDtoAndUserIdThenTrueReturned() {
        Long folderId = folderDao.addFolder(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        Boolean actualUpdatingResult = folderDao.updateFolderNameByFolderIdAndUserId(
                CORRECT_UPDATED_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        Folder folderAfterUpdating = this.getFolderByFolderId(folderId);
        assertTrue(actualUpdatingResult);
        assertNotNull(folderAfterUpdating);
        assertEquals(CORRECT_UPDATED_FOLDER_STRUCTURE_DTO.getName(), folderAfterUpdating.getName());
        assertEquals(CORRECT_UPDATED_FOLDER_STRUCTURE_DTO.getId(), folderAfterUpdating.getId());
        assertEquals(folderId, folderAfterUpdating.getId());
        assertEquals(CORRECT_USER_ID, folderAfterUpdating.getUserId());
    }

    /**
     * Update folder name by folder id and user id with incorrect folder structure dto and user id then false returned.
     */
    @Test
    public void updateFolderNameByFolderIdAndUserIdWithIncorrectFolderStructureDtoAndUserIdThenFalseReturned() {
        Long folderId = folderDao.addFolder(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        Boolean actualUpdatingResult = folderDao.updateFolderNameByFolderIdAndUserId(
                INCORRECT_FOLDER_STRUCTURE_DTO, CORRECT_USER_ID);
        Folder folderAfterUpdating = this.getFolderByFolderId(folderId);
        assertFalse(actualUpdatingResult);
        assertNotNull(folderAfterUpdating);
        assertEquals(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO.getName(), folderAfterUpdating.getName());
        assertEquals(STILL_NOT_CREATED_FOLDER_STRUCTURE_DTO.getId(), folderAfterUpdating.getParentId());
        assertEquals(folderId, folderAfterUpdating.getId());
        assertEquals(CORRECT_USER_ID, folderAfterUpdating.getUserId());
    }

    /**
     * Update folder name by folder id and user id with correct folder structure dto and incorrect user id then false returned.
     */
    @Test
    public void updateFolderNameByFolderIdAndUserIdWithCorrectFolderStructureDtoAndIncorrectUserIdThenFalseReturned() {
        Boolean actualUpdatingResult = folderDao.updateFolderNameByFolderIdAndUserId(
                CORRECT_UPDATED_FOLDER_STRUCTURE_DTO, INCORRECT_USER_ID);
        assertFalse(actualUpdatingResult);
    }

    /**
     * Update folder name by folder id and user id with null folder structure dto and user id then throw null pointer exception and false returned.
     */
    @Test(expected = NullPointerException.class)
    public void updateFolderNameByFolderIdAndUserIdWithNullFolderStructureDtoAndUserIdThenThrowNullPointerExceptionAndFalseReturned() {
        Boolean actualUpdatingResult = folderDao.updateFolderNameByFolderIdAndUserId(
                null, null);
        assertFalse(actualUpdatingResult);
    }

    private Folder getFolderByFolderId(Long folderId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", folderId);
        return namedParameterJdbcTemplate.queryForObject(getFolderByFolderIdSql, parameterSource, (rs, rowNum) -> {
            Folder folder = new Folder();
            folder.setId(rs.getLong("id"));
            folder.setName(rs.getString("name"));
            folder.setUserId(rs.getLong("user_id"));
            folder.setParentId(rs.getLong("parent_id"));
            return folder;
        });
    }

}
