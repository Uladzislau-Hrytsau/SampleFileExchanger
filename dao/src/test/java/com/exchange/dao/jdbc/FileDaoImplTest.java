package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.TestSpringDaoConfiguration;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.file.FileUpdatingDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type File dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@Transactional
@Rollback
public class FileDaoImplTest {

    private static final int REPEATABLE = 10;

    private static final int COUNT_ALL_FILES = 5;
    private static final int OFFSET_ZERO = 0;
    private static final int LIMIT_THREE = 3;
    private static final Long CORRECT_FILE_ID = 1L;
    private static final Long INCORRECT_FILE_ID = -1L;
    private static final Long CORRECT_USER_ID = 1L;
    private static final Long INCORRECT_USER_ID = -1L;
    private static final Long CORRECT_FOLDER_ID = 1L;
    private static final Long INCORRECT_FOLDER_ID = -1L;

    private static final FileDto STILL_NOT_CREATED_FILE_DTO = new FileDto(
            1L,
            1L,
            "description6",
            "real_name6",
            "encode_name6",
            LocalDate.of(1000, 10, 10)
    );
    private static final FileDto ALREADY_CREATED_FILE_DTO = new FileDto(
            1L,
            1L,
            "description1",
            "real_name1",
            "encode_name1",
            LocalDate.of(1000, 10, 10)
    );
    private static final FileUpdatingDto CORRECT_UPDATED_FILE = new FileUpdatingDto(
            1L,
            "updatedDescription",
            "updatedRealName",
            LocalDate.of(1111, 1, 1)
    );
    private static final FileUpdatingDto INCORRECT_UPDATED_FILE = new FileUpdatingDto(
            -1L,
            "updatedDescription",
            "updatedRealName",
            LocalDate.of(1111, 1, 1)
    );

    @Value("${file.getFileById}")
    private String getFileByIdSql;

    @Autowired
    private FileDao fileDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets files by limit and offset correct limit and offset correct files returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByLimitAndOffset_correctLimitAndOffset_correctFilesReturned() {
        Integer offset = OFFSET_ZERO;
        Integer limit = COUNT_ALL_FILES;
        List<File> files = fileDao.getFilesByLimitAndOffset(limit, offset);
        assertNotNull(files);
        Integer expectedSize = files.size();
        assertEquals(limit, expectedSize);
    }

    /**
     * Gets files by limit and offset negative offset correct files returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByLimitAndOffset_negativeOffset_correctFilesReturned() {
        Integer limit = LIMIT_THREE;
        Integer offset = -(COUNT_ALL_FILES);
        List<File> files = fileDao.getFilesByLimitAndOffset(limit, offset);
        assertNotNull(files);
        Integer expectedSize = files.size();
        assertEquals(limit, expectedSize);
    }

    /**
     * Gets files by limit and offset negative limit all files returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByLimitAndOffset_negativeLimit_allFilesReturned() {
        Integer actualSizeFiles = COUNT_ALL_FILES;
        Integer limit = -(LIMIT_THREE);
        Integer offset = OFFSET_ZERO;
        List<File> files = fileDao.getFilesByLimitAndOffset(limit, offset);
        assertNotNull(files);
        Integer expectedSize = files.size();
        assertNotEquals(actualSizeFiles, limit);
        assertEquals(actualSizeFiles, expectedSize);
    }

    /**
     * Gets files by limit and offset null limit and offset all files returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByLimitAndOffset_nullLimitAndOffset_allFilesReturned() {
        Integer actualSizeFiles = COUNT_ALL_FILES;
        List<File> files = fileDao.getFilesByLimitAndOffset(null, null);
        Integer expectedSize = files.size();
        assertNotNull(files);
        assertEquals(actualSizeFiles, expectedSize);
    }

    /**
     * Add file correct file dto correct file dto id returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void addFile_correctFileDto_correctFileDtoIdReturned() {
        Long beforeAdding = fileDao.getFileCount();
        Long fileId = fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        Long afterAdding = fileDao.getFileCount();
        File actualFile = this.getFileById(fileId);
        assertNotNull(fileId);
        assertNotNull(beforeAdding);
        assertNotNull(afterAdding);
        assertEquals(++beforeAdding, afterAdding);
        assertNotNull(actualFile);
        assertTrue(this.assertFiles(STILL_NOT_CREATED_FILE_DTO, actualFile));
    }

    /**
     * Add file incorrect file dto count not changed and duplicate key exception returned.
     */
    @Test(expected = DuplicateKeyException.class)
    @Repeat(value = REPEATABLE)
    public void addFile_incorrectFileDto_CountNotChangedAndDuplicateKeyExceptionReturned() {
        fileDao.addFile(ALREADY_CREATED_FILE_DTO);
    }

    /**
     * Add file incorrect file dto __ count not changed and null pointer exception returned.
     */
    @Test(expected = NullPointerException.class)
    @Repeat(value = REPEATABLE)
    public void addFile_incorrectFileDto__CountNotChangedAndNullPointerExceptionReturned() {
        fileDao.addFile(null);
    }

    /**
     * Update file correct file updating dto true returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void updateFile_correctFileUpdatingDto_trueReturned() {
        File beforeUpdating = this.getFileById(CORRECT_UPDATED_FILE.getId());
        Boolean updatingResult = fileDao.updateFile(CORRECT_UPDATED_FILE);
        File afterUpdating = this.getFileById(CORRECT_UPDATED_FILE.getId());
        assertNotNull(beforeUpdating);
        assertNotNull(updatingResult);
        assertNotNull(afterUpdating);
        assertTrue(updatingResult);
        FileDto updatedFile = this.convertToFile(CORRECT_UPDATED_FILE);
        assertNotNull(updatedFile);
        assertEquals(beforeUpdating.getId(), CORRECT_UPDATED_FILE.getId());
        assertNotEquals(beforeUpdating.getDate(), CORRECT_UPDATED_FILE.getDate());
        assertNotEquals(beforeUpdating.getDescription(), CORRECT_UPDATED_FILE.getDescription());
        assertNotEquals(beforeUpdating.getRealName(), CORRECT_UPDATED_FILE.getRealName());
        assertEquals(afterUpdating.getId(), CORRECT_UPDATED_FILE.getId());
        assertEquals(afterUpdating.getDate(), CORRECT_UPDATED_FILE.getDate());
        assertEquals(afterUpdating.getDescription(), CORRECT_UPDATED_FILE.getDescription());
        assertEquals(afterUpdating.getRealName(), CORRECT_UPDATED_FILE.getRealName());
    }

    /**
     * Update file incorrect file updating dto false returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void updateFile_incorrectFileUpdatingDto_falseReturned() {
        Boolean updatingResult = fileDao.updateFile(INCORRECT_UPDATED_FILE);
        assertNotNull(updatingResult);
        assertFalse(updatingResult);
    }

    /**
     * Update file incorrect file updating dto null pointer exception and false returned.
     */
    @Test(expected = NullPointerException.class)
    @Repeat(value = REPEATABLE)
    public void updateFile_incorrectFileUpdatingDto_nullPointerExceptionAndFalseReturned() {
        Boolean updatingResult = fileDao.updateFile(null);
        assertNotNull(updatingResult);
        assertFalse(updatingResult);
    }

    /**
     * Delete file correct file id true returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void deleteFile_correctFileId_trueReturned() {
        Long countBeforeDeleteFile = fileDao.getFileCount();
        Boolean actualDeleteFileResult = fileDao.deleteFile(CORRECT_FILE_ID);
        Long countAfterDeleteFile = fileDao.getFileCount();
        assertNotNull(countBeforeDeleteFile);
        assertNotNull(actualDeleteFileResult);
        assertTrue(actualDeleteFileResult);
        assertNotNull(countAfterDeleteFile);
        assertEquals(countBeforeDeleteFile, ++countAfterDeleteFile);
    }

    /**
     * Delete file incorrect file id false returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void deleteFile_incorrectFileId_falseReturned() {
        Long countBeforeDeleteFile = fileDao.getFileCount();
        Boolean actualDeleteFileResult = fileDao.deleteFile(INCORRECT_FILE_ID);
        Long countAfterDeleteFile = fileDao.getFileCount();
        assertNotNull(countBeforeDeleteFile);
        assertNotNull(actualDeleteFileResult);
        assertFalse(actualDeleteFileResult);
        assertNotNull(countAfterDeleteFile);
        assertEquals(countBeforeDeleteFile, countAfterDeleteFile);
    }

    /**
     * Delete file null file id false returned.
     */
    @Test
    @Repeat(value = REPEATABLE)
    public void deleteFile_nullFileId_falseReturned() {
        Long countBeforeDeleteFile = fileDao.getFileCount();
        Boolean actualDeleteFileResult = fileDao.deleteFile(null);
        Long countAfterDeleteFile = fileDao.getFileCount();
        assertNotNull(countBeforeDeleteFile);
        assertNotNull(actualDeleteFileResult);
        assertFalse(actualDeleteFileResult);
        assertNotNull(countAfterDeleteFile);
        assertEquals(countBeforeDeleteFile, countAfterDeleteFile);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByUserIdAndFolderId_correctUserIdAndFolderId_correctFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_USER_ID, CORRECT_FOLDER_ID);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_USER_ID, CORRECT_FOLDER_ID);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(++countBeforeAdding, countAfterAdding);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByUserIdAndFolderId_incorrectUserIdAndCorrectFolderId_correctFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(INCORRECT_USER_ID, CORRECT_FOLDER_ID);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(INCORRECT_USER_ID, CORRECT_FOLDER_ID);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(countBeforeAdding, countAfterAdding);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByUserIdAndFolderId_correctUserIdAndIncorrectFolderId_correctFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_FILE_ID, INCORRECT_FOLDER_ID);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_FILE_ID, INCORRECT_FOLDER_ID);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(countBeforeAdding, countAfterAdding);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFilesByUserIdAndFolderId_nullUserIdAndFolderId_correctFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(null, null);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(null, null);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(countBeforeAdding, countAfterAdding);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFileCount_correctCountReturned() {
        Long countBeforeDeleting = fileDao.getFileCount();
        Boolean deletingResult = fileDao.deleteFile(CORRECT_FILE_ID);
        Long countAfterDeleting = fileDao.getFileCount();
        assertNotNull(countBeforeDeleting);
        assertNotNull(countAfterDeleting);
        assertTrue(deletingResult);
        assertEquals(countBeforeDeleting, ++countAfterDeleting);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFileNameByFileId_correctFileId_correctFileNameReturned() {
        Long fileId = fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        assertNotNull(fileId);
        String fileNameAfterAdding = fileDao.getFileNameByFileId(fileId);
        assertNotNull(fileNameAfterAdding);
        assertEquals(STILL_NOT_CREATED_FILE_DTO.getEncodeName(), fileNameAfterAdding);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Repeat(value = REPEATABLE)
    public void getFileNameByFileId_incorrectFileId_emptyResultDataAccessExceptionReturned() {
        fileDao.getFileNameByFileId(INCORRECT_FILE_ID);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Repeat(value = REPEATABLE)
    public void getFileNameByFileId_nullFileId_correctFileNameReturned() {
        fileDao.getFileNameByFileId(null);
    }

    @Test
    @Repeat(value = REPEATABLE)
    @Ignore
    public void getFileNamesByFolderIdAndUserId_correctFolderIdAndUserId_correctFileNamesReturned() {
        List<String> fileNames = fileDao.getFileNamesByFolderIdAndUserId(CORRECT_FOLDER_ID, CORRECT_USER_ID);
        assertNotNull(fileNames);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFileInformationByFileIdAndUserId_correctFileIdAndUserId_correctFileInformationReturned() {
        Long fileId = fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        assertNotNull(fileId);
        FileUpdatingDto fileAfterAdding = fileDao.getFileInformationByFileIdAndUserId(fileId, STILL_NOT_CREATED_FILE_DTO.getUserId());
        assertNotNull(fileAfterAdding);
        assertEquals(fileAfterAdding.getId(), fileId);
        assertEquals(fileAfterAdding.getDate(), STILL_NOT_CREATED_FILE_DTO.getDate());
        assertEquals(fileAfterAdding.getDescription(), STILL_NOT_CREATED_FILE_DTO.getDescription());
        assertEquals(fileAfterAdding.getRealName(), STILL_NOT_CREATED_FILE_DTO.getRealName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Repeat(value = REPEATABLE)
    public void getFileInformationByFileIdAndUserId_incorrectFileIdAndCorrectUserId_EmptyResultDataAccessExceptionReturned() {
        fileDao.getFileInformationByFileIdAndUserId(INCORRECT_FILE_ID, CORRECT_USER_ID);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Repeat(value = REPEATABLE)
    public void getFileInformationByFileIdAndUserId_correctFileIdAndIncorrectUserId_EmptyResultDataAccessExceptionReturned() {
        fileDao.getFileInformationByFileIdAndUserId(CORRECT_FILE_ID, INCORRECT_USER_ID);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Repeat(value = REPEATABLE)
    public void getFileInformationByFileIdAndUserId_incorrectFileIdAndIncorrectUserId_EmptyResultDataAccessExceptionReturned() {
        fileDao.getFileInformationByFileIdAndUserId(INCORRECT_FILE_ID, INCORRECT_USER_ID);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFileNamesByUserId_correctUserId_correctFileNamesReturned() {
        List<String> fileNamesBeforeAdding = fileDao.getFileNamesByUserId(STILL_NOT_CREATED_FILE_DTO.getUserId());
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<String> fileNamesAfterAdding = fileDao.getFileNamesByUserId(STILL_NOT_CREATED_FILE_DTO.getUserId());
        assertNotNull(fileNamesBeforeAdding);
        assertNotNull(fileNamesAfterAdding);
        Integer countBeforeAdding = fileNamesBeforeAdding.size();
        Integer countAfterAdding = fileNamesAfterAdding.size();
        assertEquals(++countBeforeAdding, countAfterAdding);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFileNamesByUserId_incorrectUserId_correctFileNamesReturned() {
        List<String> fileNames = fileDao.getFileNamesByUserId(INCORRECT_USER_ID);
        assertNotNull(fileNames);
        Integer actualFileNamesSize = fileNames.size();
        Integer expectedFileNamesSize = 0;
        assertEquals(expectedFileNamesSize, actualFileNamesSize);
    }

    @Test
    @Repeat(value = REPEATABLE)
    public void getFileNamesByUserId_nullUserId_correctFileNamesReturned() {
        List<String> fileNames = fileDao.getFileNamesByUserId(null);
        assertNotNull(fileNames);
        Integer actualFileNamesSize = fileNames.size();
        Integer expectedFileNamesSize = 0;
        assertEquals(expectedFileNamesSize, actualFileNamesSize);
    }

    private Boolean assertFiles(FileDto fileDto, File file) {
        assertEquals(fileDto.getUserId(), file.getUserId());
        assertEquals(fileDto.getDate(), file.getDate());
        assertEquals(fileDto.getDescription(), file.getDescription());
        assertEquals(fileDto.getEncodeName(), file.getEncodeName());
        assertEquals(fileDto.getFolderId(), file.getFolderId());
        assertEquals(fileDto.getRealName(), file.getRealName());
        return true;
    }

    private FileDto convertToFile(FileUpdatingDto fileUpdatingDto) {
        FileDto file = new FileDto();
        file.setDescription(fileUpdatingDto.getDescription());
        file.setRealName(fileUpdatingDto.getRealName());
        file.setDate(fileUpdatingDto.getDate());
        return file;
    }

    private File getFileById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                getFileByIdSql, parameterSource, (rs, rowNum) -> {
                    File file = new File();
                    file.setId(rs.getLong("id"));
                    file.setUserId(rs.getLong("user_id"));
                    file.setDescription(rs.getString("description"));
                    file.setFolderId(rs.getLong("folder_id"));
                    file.setRealName(rs.getString("real_name"));
                    file.setEncodeName(rs.getString("encode_name"));
                    Date date = rs.getDate("date");
                    if (date != null) {
                        file.setDate(date.toLocalDate());
                    }
                    return file;
                });
    }

}
