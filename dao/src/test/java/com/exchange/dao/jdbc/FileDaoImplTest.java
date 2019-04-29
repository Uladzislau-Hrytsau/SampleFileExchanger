package com.exchange.dao.jdbc;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.TestSpringDaoConfiguration;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.file.FileUpdatingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type File dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FileDaoImplTest {

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
     * Gets files by limit and offset with correct limit and offset then correct files returned.
     */
    @Test
    public void getFilesByLimitAndOffsetWithCorrectLimitAndOffsetThenCorrectFilesReturned() {
        Integer offset = OFFSET_ZERO;
        Integer limit = COUNT_ALL_FILES;
        List<File> files = fileDao.getFilesByLimitAndOffset(limit, offset);
        assertNotNull(files);
        Integer expectedSize = files.size();
        assertEquals(limit, expectedSize);
    }

    /**
     * Gets files by limit and offset with negative offset then correct files returned.
     */
    @Test
    public void getFilesByLimitAndOffsetWithNegativeOffsetThenCorrectFilesReturned() {
        Integer limit = LIMIT_THREE;
        Integer offset = -(COUNT_ALL_FILES);
        List<File> files = fileDao.getFilesByLimitAndOffset(limit, offset);
        assertNotNull(files);
        Integer expectedSize = files.size();
        assertEquals(limit, expectedSize);
    }

    /**
     * Gets files by limit and offset with negative limit then all files returned.
     */
    @Test
    public void getFilesByLimitAndOffsetWithNegativeLimitThenAllFilesReturned() {
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
     * Gets files by limit and offset with null limit and offset then all files.
     */
    @Test
    public void getFilesByLimitAndOffsetWithNullLimitAndOffsetThenAllFiles() {
        Integer actualSizeFiles = COUNT_ALL_FILES;
        List<File> files = fileDao.getFilesByLimitAndOffset(null, null);
        Integer expectedSize = files.size();
        assertNotNull(files);
        assertEquals(actualSizeFiles, expectedSize);
    }

    /**
     * Add file with correct file dto then correct file dto id returned.
     */
    @Test
    public void addFileWithCorrectFileDtoThenCorrectFileDtoIdReturned() {
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
     * Add file with incorrect file dto then count not changed and duplicate key exception.
     */
    @Test(expected = DuplicateKeyException.class)
    public void addFileWithIncorrectFileDtoThenCountNotChangedAndDuplicateKeyException() {
        fileDao.addFile(ALREADY_CREATED_FILE_DTO);
    }

    /**
     * Add file with incorrect file dto then throw count not changed and null pointer exception.
     */
    @Test(expected = NullPointerException.class)
    public void addFileWithIncorrectFileDtoThenThrowCountNotChangedAndNullPointerException() {
        fileDao.addFile(null);
    }

    /**
     * Update file with correct file updating dto then true returned.
     */
    @Test
    public void updateFileWithCorrectFileUpdatingDtoThenTrueReturned() {
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
     * Update file with incorrect file updating dto then false returned.
     */
    @Test
    public void updateFileWithIncorrectFileUpdatingDtoThenFalseReturned() {
        Boolean updatingResult = fileDao.updateFile(INCORRECT_UPDATED_FILE);
        assertNotNull(updatingResult);
        assertFalse(updatingResult);
    }

    /**
     * Update file with incorrect file updating dto then throw null pointer exception and false returned.
     */
    @Test(expected = NullPointerException.class)
    public void updateFileWithIncorrectFileUpdatingDtoThenThrowNullPointerExceptionAndFalseReturned() {
        Boolean updatingResult = fileDao.updateFile(null);
        assertNotNull(updatingResult);
        assertFalse(updatingResult);
    }

    /**
     * Delete file with correct file id then true returned.
     */
    @Test
    public void deleteFileWithCorrectFileIdThenTrueReturned() {
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
     * Delete file with incorrect file id then false returned.
     */
    @Test
    public void deleteFileWithIncorrectFileIdThenFalseReturned() {
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
     * Delete file with null file id then false returned.
     */
    @Test
    public void deleteFileWithNullFileIdThenFalseReturned() {
        Long countBeforeDeleteFile = fileDao.getFileCount();
        Boolean actualDeleteFileResult = fileDao.deleteFile(null);
        Long countAfterDeleteFile = fileDao.getFileCount();
        assertNotNull(countBeforeDeleteFile);
        assertNotNull(actualDeleteFileResult);
        assertFalse(actualDeleteFileResult);
        assertNotNull(countAfterDeleteFile);
        assertEquals(countBeforeDeleteFile, countAfterDeleteFile);
    }

    /**
     * Gets files by user id and folder id with correct user id and folder id then correct file structure dto returned.
     */
    @Test
    public void getFilesByUserIdAndFolderIdWithCorrectUserIdAndFolderIdThenCorrectFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_USER_ID, CORRECT_FOLDER_ID);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_USER_ID, CORRECT_FOLDER_ID);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(++countBeforeAdding, countAfterAdding);
    }

    /**
     * Gets files by user id and folder id with incorrect user id and correct folder id then correct file structure dto returned.
     */
    @Test
    public void getFilesByUserIdAndFolderIdWithIncorrectUserIdAndCorrectFolderIdThenCorrectFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(INCORRECT_USER_ID, CORRECT_FOLDER_ID);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(INCORRECT_USER_ID, CORRECT_FOLDER_ID);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(countBeforeAdding, countAfterAdding);
    }

    /**
     * Gets files by user id and folder id with correct user id and incorrect folder id then correct file structure dto returned.
     */
    @Test
    public void getFilesByUserIdAndFolderIdWithCorrectUserIdAndIncorrectFolderIdThenCorrectFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_FILE_ID, INCORRECT_FOLDER_ID);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(CORRECT_FILE_ID, INCORRECT_FOLDER_ID);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(countBeforeAdding, countAfterAdding);
    }

    /**
     * Gets files by user id and folder id with null user id and folder id then correct file structure dto returned.
     */
    @Test
    public void getFilesByUserIdAndFolderIdWithNullUserIdAndFolderIdThenCorrectFileStructureDtoReturned() {
        List<FileStructureDto> fileStructureDtoBeforeAdding = fileDao.getFilesByUserIdAndFolderId(null, null);
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<FileStructureDto> fileStructureDtoAfterAdding = fileDao.getFilesByUserIdAndFolderId(null, null);
        assertNotNull(fileStructureDtoBeforeAdding);
        assertNotNull(fileStructureDtoAfterAdding);
        Integer countBeforeAdding = fileStructureDtoBeforeAdding.size();
        Integer countAfterAdding = fileStructureDtoAfterAdding.size();
        assertEquals(countBeforeAdding, countAfterAdding);
    }

    /**
     * Gets file count with correct count returned.
     */
    @Test
    public void getFileCountWithCorrectCountReturned() {
        Long countBeforeDeleting = fileDao.getFileCount();
        Boolean deletingResult = fileDao.deleteFile(CORRECT_FILE_ID);
        Long countAfterDeleting = fileDao.getFileCount();
        assertNotNull(countBeforeDeleting);
        assertNotNull(countAfterDeleting);
        assertTrue(deletingResult);
        assertEquals(countBeforeDeleting, ++countAfterDeleting);
    }

    /**
     * Gets file name by file id with correct file id then correct file name returned.
     */
    @Test
    public void getFileNameByFileIdWithCorrectFileIdThenCorrectFileNameReturned() {
        Long fileId = fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        assertNotNull(fileId);
        String fileNameAfterAdding = fileDao.getFileNameByFileId(fileId);
        assertNotNull(fileNameAfterAdding);
        assertEquals(STILL_NOT_CREATED_FILE_DTO.getEncodeName(), fileNameAfterAdding);
    }

    /**
     * Gets file name by file id with incorrect file id then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getFileNameByFileIdWithIncorrectFileIdThenThrowEmptyResultDataAccessException() {
        fileDao.getFileNameByFileId(INCORRECT_FILE_ID);
    }

    /**
     * Gets file name by file id with null file id then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getFileNameByFileIdWithNullFileIdThenThrowEmptyResultDataAccessException() {
        fileDao.getFileNameByFileId(null);
    }

    /**
     * Gets file names by folder id and user id with correct folder id and user id then correct file names returned.
     */
    @Test
    public void getFileNamesByFolderIdAndUserIdWithCorrectFolderIdAndUserIdThenCorrectFileNamesReturned() {
        List<String> actualFileNames = fileDao.getFileNamesByFolderIdAndUserId(CORRECT_FOLDER_ID, CORRECT_USER_ID);
        assertNotNull(actualFileNames);
        List<FileStructureDto> fileStructureDtos = fileDao.getFilesByUserIdAndFolderId(CORRECT_USER_ID, CORRECT_FOLDER_ID);
        List<String> expectedFileNames = new ArrayList<>();
        fileStructureDtos.forEach(item -> expectedFileNames.add(fileDao.getFileNameByFileId(item.getId())));
        assertEquals(expectedFileNames, actualFileNames);
    }

    /**
     * Gets file names by folder id and user id with incorrect folder id and correct user id then data integrity violation exception.
     */
    @Test
    public void getFileNamesByFolderIdAndUserIdWithIncorrectFolderIdAndCorrectUserIdThenDataIntegrityViolationException() {
        fileDao.getFileNamesByFolderIdAndUserId(INCORRECT_FOLDER_ID, CORRECT_USER_ID);
    }

    /**
     * Gets file names by folder id and user id with correct folder id and incorrect user id then correct file names returned.
     */
    @Test
    public void getFileNamesByFolderIdAndUserIdWithCorrectFolderIdAndIncorrectUserIdThenCorrectFileNamesReturned() {
        List<String> actualFileNames = fileDao.getFileNamesByFolderIdAndUserId(CORRECT_FOLDER_ID, INCORRECT_USER_ID);
        assertNotNull(actualFileNames);
        List<FileStructureDto> fileStructureDtos = fileDao.getFilesByUserIdAndFolderId(CORRECT_USER_ID, INCORRECT_USER_ID);
        List<String> expectedFileNames = new ArrayList<>();
        fileStructureDtos.forEach(item -> expectedFileNames.add(fileDao.getFileNameByFileId(item.getId())));
        assertEquals(expectedFileNames, actualFileNames);
    }

    /**
     * Gets file names by folder id and user id with incorrect folder id and user id then correct file names returned.
     */
    @Test
    public void getFileNamesByFolderIdAndUserIdWithIncorrectFolderIdAndUserIdThenCorrectFileNamesReturned() {
        List<String> actualFileNames = fileDao.getFileNamesByFolderIdAndUserId(INCORRECT_FOLDER_ID, INCORRECT_USER_ID);
        assertNotNull(actualFileNames);
        List<FileStructureDto> fileStructureDtos = fileDao.getFilesByUserIdAndFolderId(INCORRECT_FOLDER_ID, INCORRECT_USER_ID);
        List<String> expectedFileNames = new ArrayList<>();
        fileStructureDtos.forEach(item -> expectedFileNames.add(fileDao.getFileNameByFileId(item.getId())));
        assertEquals(expectedFileNames, actualFileNames);
    }

    /**
     * Gets file names by folder id and user id with null folder id and user id then correct file names returned.
     */
    @Test
    public void getFileNamesByFolderIdAndUserIdWithNullFolderIdAndUserIdThenCorrectFileNamesReturned() {
        List<String> actualFileNames = fileDao.getFileNamesByFolderIdAndUserId(null, null);
        assertNotNull(actualFileNames);
        List<FileStructureDto> fileStructureDtos = fileDao.getFilesByUserIdAndFolderId(null, null);
        List<String> expectedFileNames = new ArrayList<>();
        fileStructureDtos.forEach(item -> expectedFileNames.add(fileDao.getFileNameByFileId(item.getId())));
        assertEquals(expectedFileNames, actualFileNames);
    }

    /**
     * Gets file information by file id and user id with correct file id and user id then correct file information returned.
     */
    @Test
    public void getFileInformationByFileIdAndUserIdWithCorrectFileIdAndUserIdThenCorrectFileInformationReturned() {
        Long fileId = fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        assertNotNull(fileId);
        FileUpdatingDto fileAfterAdding = fileDao.getFileInformationByFileIdAndUserId(fileId, STILL_NOT_CREATED_FILE_DTO.getUserId());
        assertNotNull(fileAfterAdding);
        assertEquals(fileAfterAdding.getId(), fileId);
        assertEquals(fileAfterAdding.getDate(), STILL_NOT_CREATED_FILE_DTO.getDate());
        assertEquals(fileAfterAdding.getDescription(), STILL_NOT_CREATED_FILE_DTO.getDescription());
        assertEquals(fileAfterAdding.getRealName(), STILL_NOT_CREATED_FILE_DTO.getRealName());
    }

    /**
     * Gets file information by file id and user id with incorrect file id and correct user id then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getFileInformationByFileIdAndUserIdWithIncorrectFileIdAndCorrectUserIdThenThrowEmptyResultDataAccessException() {
        fileDao.getFileInformationByFileIdAndUserId(INCORRECT_FILE_ID, CORRECT_USER_ID);

    }

    /**
     * Gets file information by file id and user id with correct file id and incorrect user id then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getFileInformationByFileIdAndUserIdWithCorrectFileIdAndIncorrectUserIdThenThrowEmptyResultDataAccessException() {
        fileDao.getFileInformationByFileIdAndUserId(CORRECT_FILE_ID, INCORRECT_USER_ID);
    }

    /**
     * Gets file information by file id and user id with incorrect file id and incorrect user id then throw empty result data access exception.
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getFileInformationByFileIdAndUserIdWithIncorrectFileIdAndIncorrectUserIdThenThrowEmptyResultDataAccessException() {
        fileDao.getFileInformationByFileIdAndUserId(INCORRECT_FILE_ID, INCORRECT_USER_ID);
    }

    /**
     * Gets file names by user id with correct user id then correct file names returned.
     */
    @Test
    public void getFileNamesByUserIdWithCorrectUserIdThenCorrectFileNamesReturned() {
        List<String> fileNamesBeforeAdding = fileDao.getFileNamesByUserId(STILL_NOT_CREATED_FILE_DTO.getUserId());
        fileDao.addFile(STILL_NOT_CREATED_FILE_DTO);
        List<String> fileNamesAfterAdding = fileDao.getFileNamesByUserId(STILL_NOT_CREATED_FILE_DTO.getUserId());
        assertNotNull(fileNamesBeforeAdding);
        assertNotNull(fileNamesAfterAdding);
        Integer countBeforeAdding = fileNamesBeforeAdding.size();
        Integer countAfterAdding = fileNamesAfterAdding.size();
        assertEquals(++countBeforeAdding, countAfterAdding);
    }

    /**
     * Gets file names by user id with incorrect user id then correct file names returned.
     */
    @Test
    public void getFileNamesByUserIdWithIncorrectUserIdThenCorrectFileNamesReturned() {
        List<String> fileNames = fileDao.getFileNamesByUserId(INCORRECT_USER_ID);
        assertNotNull(fileNames);
        Integer actualFileNamesSize = fileNames.size();
        Integer expectedFileNamesSize = 0;
        assertEquals(expectedFileNamesSize, actualFileNamesSize);
    }

    /**
     * Gets file names by user id with null user id then correct file names returned.
     */
    @Test
    public void getFileNamesByUserIdWithNullUserIdThenCorrectFileNamesReturned() {
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
