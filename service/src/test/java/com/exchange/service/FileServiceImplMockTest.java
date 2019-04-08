package com.exchange.service;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.Pagination;
import com.exchange.dao.exception.FileNotDeletedException;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.implementation.FileServiceImpl;
import com.exchange.service.validation.CommonValidator;
import com.exchange.service.validation.FileValidator;
import com.exchange.wrapper.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * The type File service impl mock test.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(MockitoJUnitRunner.class)
@PrepareForTest({URLConnection.class, FileInputStream.class})
public class FileServiceImplMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Integer CORRECT_PAGE = 2;
    private static final Integer CORRECT_SIZE = 10;
    private static final Long CORRECT_ID = 1L;
    private static final Long CORRECT_USER_ID = 2L;
    private static final Long CORRECT_FOLDER_ID = 3L;
    private static final String CORRECT_DESCRIPTION = "correctDescription";
    private static final String CORRECT_REAL_NAME = "correctRealName.pdf";
    private static final String CORRECT_ENCODE_NAME = "correctEncodeName";
    private static final Integer CORRECT_FILE_LENGTH = 1024;
    private static final LocalDate CORRECT_DATE = LocalDate.of(1000, 10, 10);
    private static final File CORRECT_FILE = new File(
            CORRECT_ID,
            CORRECT_USER_ID,
            CORRECT_FOLDER_ID,
            CORRECT_DESCRIPTION,
            CORRECT_REAL_NAME,
            CORRECT_ENCODE_NAME,
            CORRECT_DATE);
    private static final List<File> CORRECT_FILES = Arrays.asList(
            CORRECT_FILE,
            CORRECT_FILE);
    private static final Set<Long> CORRECT_CATEGORIES = new HashSet<>(Arrays.asList(1L, 2L, 3L));
    private static final FileDto CORRECT_FILE_DTO = new FileDto(
            CORRECT_USER_ID,
            CORRECT_FOLDER_ID,
            CORRECT_DESCRIPTION,
            CORRECT_REAL_NAME,
            CORRECT_ENCODE_NAME,
            CORRECT_DATE,
            CORRECT_CATEGORIES);
    private static final FileUpdatingDto CORRECT_FILE_UPDATING_DTO = new FileUpdatingDto(
            CORRECT_ID,
            CORRECT_DESCRIPTION,
            CORRECT_REAL_NAME,
            CORRECT_DATE);

    @Mock
    private FileDao fileDaoMock;
    @Mock
    private FileValidator fileValidatorMock;
    @Mock
    private CategoryService categoryServiceMock;
    @Mock
    private FileWriterService fileWriterServiceMock;
    @Mock
    private CommonService commonServiceMock;
    @Mock
    private CommonValidator commonValidatorMock;
    @Mock
    private Authentication authenticationMock;
    @Mock
    private FileDto fileDtoMock;

    @InjectMocks
    private FileServiceImpl fileService;

    /**
     * Gets files and count by page and size correct page and size correct response returned.
     */
    @Test
    public void getFilesAndCountByPageAndSize_correctPageAndSize_correctResponseReturned() {
        doNothing().when(commonValidatorMock).validatePageAndSize(any(), any());
        when(fileDaoMock.getFilesByLimitAndOffset(any(), any())).thenReturn(CORRECT_FILES);
        when(fileDaoMock.getFileCount()).thenReturn((long) CORRECT_FILES.size());
        Response actualFilesAndCountByPageAndSize =
                fileService.getFilesAndCountByPageAndSize(CORRECT_PAGE, CORRECT_SIZE);
        Response expectedFilesAndCountByPageAndSize =
                new Response(CORRECT_FILES, new Pagination((long) CORRECT_FILES.size()));
        assertEquals(expectedFilesAndCountByPageAndSize, actualFilesAndCountByPageAndSize);
        verify(commonValidatorMock, times(TIMES_ONE)).validatePageAndSize(any(), any());
        verify(fileDaoMock, times(TIMES_ONE)).getFilesByLimitAndOffset(any(), any());
        verify(fileDaoMock, times(TIMES_ONE)).getFileCount();
        verifyNoMoreInteractions(
                fileDaoMock,
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonValidatorMock,
                authenticationMock);
    }

    /**
     * Gets files and count by page and size incorrect page and size validation exception.
     */
    @Test(expected = ValidationException.class)
    public void getFilesAndCountByPageAndSize_incorrectPageAndSize_validationException() {
        doThrow(ValidationException.class).when(commonValidatorMock).validatePageAndSize(any(), any());
        fileService.getFilesAndCountByPageAndSize(CORRECT_PAGE, CORRECT_SIZE);
        verify(commonValidatorMock, times(TIMES_ONE)).validatePageAndSize(any(), any());
        verify(fileDaoMock, never()).getFilesByLimitAndOffset(any(), any());
        verify(fileDaoMock, never()).getFileCount();
        verifyNoMoreInteractions(
                fileDaoMock,
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock,
                authenticationMock);
    }

    /**
     * Add file correct file dto and multipart file and authentication correct file id returned.
     *
     * @throws IOException the io exception
     */
    @Test
    public void addFile_correctFileDtoAndMultipartFileAndAuthentication_correctFileIdReturned() throws IOException {
        fileDtoMock.setDescription(CORRECT_DESCRIPTION);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                CORRECT_REAL_NAME, CORRECT_REAL_NAME, null, "file".getBytes());
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_USER_ID);
        doNothing().when(fileValidatorMock).validateDescription(any());
        when(fileDaoMock.addFile(CORRECT_FILE_DTO)).thenReturn(CORRECT_ID);
        doNothing().when(categoryServiceMock).addFileCategories(any(), any(), any());
        doNothing().when(fileWriterServiceMock).saveFile(any(), any());
        fileService.addFile(CORRECT_FILE_DTO, mockMultipartFile, authenticationMock);
        assertEquals(CORRECT_FILE_DTO.getUserId(), CORRECT_USER_ID);
        assertEquals(CORRECT_FILE_DTO.getRealName(), CORRECT_REAL_NAME);
        assertNotEquals(CORRECT_FILE_DTO.getEncodeName(), CORRECT_ENCODE_NAME);
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileDaoMock, times(TIMES_ONE)).addFile(any());
        verify(categoryServiceMock, times(TIMES_ONE)).addFileCategories(any(), any(), any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).saveFile(any(), any());
        verifyNoMoreInteractions(
                fileDaoMock,
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Add file incorrect file dto description validation exception.
     *
     * @throws IOException the io exception
     */
    @Test(expected = ValidationException.class)
    public void addFile_incorrectFileDtoDescription_validationException() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                CORRECT_REAL_NAME, CORRECT_REAL_NAME, null, "file".getBytes());
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_USER_ID);
        doThrow(ValidationException.class).when(fileValidatorMock).validateDescription(any());
        fileService.addFile(CORRECT_FILE_DTO, mockMultipartFile, authenticationMock);
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileDaoMock, never()).addFile(any());
        verify(categoryServiceMock, never()).addFileCategories(any(), any(), any());
        verify(fileWriterServiceMock, never()).saveFile(any(), any());
        verifyNoMoreInteractions(
                fileDaoMock,
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Add file incorrect file dto and multipart file and authentication internal server exception.
     *
     * @throws IOException the io exception
     */
    @Test(expected = InternalServerException.class)
    public void addFile_incorrectFileDtoAndMultipartFileAndAuthentication_internalServerException() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                CORRECT_REAL_NAME, CORRECT_REAL_NAME, null, "file".getBytes());
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_USER_ID);
        doNothing().when(fileValidatorMock).validateDescription(any());
        when(fileDaoMock.addFile(CORRECT_FILE_DTO)).thenReturn(CORRECT_ID);
        doThrow(InternalServerException.class).when(categoryServiceMock).addFileCategories(any(), any(), any());
        fileService.addFile(CORRECT_FILE_DTO, mockMultipartFile, authenticationMock);
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileDaoMock, times(TIMES_ONE)).addFile(any());
        verify(categoryServiceMock, times(TIMES_ONE)).addFileCategories(any(), any(), any());
        verify(fileWriterServiceMock, never()).saveFile(any(), any());
        verifyNoMoreInteractions(
                fileDaoMock,
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Update file correct file updating dto.
     */
    @Test
    public void updateFile_correctFileUpdatingDto() {
        CORRECT_FILE_UPDATING_DTO.setDate(null);
        mockStatic(LocalDate.class);
        doNothing().when(fileValidatorMock).validateFileId(any());
        doNothing().when(fileValidatorMock).validateDescription(any());
        doNothing().when(fileValidatorMock).validateName(any());
        when(LocalDate.now()).thenReturn(CORRECT_DATE);
        when(fileDaoMock.updateFile(any())).thenReturn(Boolean.TRUE);
        fileService.updateFile(CORRECT_FILE_UPDATING_DTO);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateName(any());
        verify(fileDaoMock, times(TIMES_ONE)).updateFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Update file incorrect file updating dto file id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void updateFile_incorrectFileUpdatingDtoFileId_validationException() {
        doThrow(ValidationException.class).when(fileValidatorMock).validateFileId(any());
        fileService.updateFile(CORRECT_FILE_UPDATING_DTO);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileValidatorMock, never()).validateDescription(any());
        verify(fileValidatorMock, never()).validateName(any());
        verify(fileDaoMock, never()).updateFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Update file incorrect file updating dto description validation exception.
     */
    @Test(expected = ValidationException.class)
    public void updateFile_incorrectFileUpdatingDtoDescription_validationException() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        doThrow(ValidationException.class).when(fileValidatorMock).validateDescription(any());
        fileService.updateFile(CORRECT_FILE_UPDATING_DTO);
        assertEquals(CORRECT_DATE, CORRECT_FILE_UPDATING_DTO.getDate());
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileValidatorMock, never()).validateName(any());
        verify(fileDaoMock, never()).updateFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Update file correct file updating dto name validation exception.
     */
    @Test(expected = ValidationException.class)
    public void updateFile_correctFileUpdatingDtoName_validationException() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        doNothing().when(fileValidatorMock).validateDescription(any());
        doThrow(ValidationException.class).when(fileValidatorMock).validateName(any());
        when(fileDaoMock.updateFile(any())).thenReturn(Boolean.TRUE);
        fileService.updateFile(CORRECT_FILE_UPDATING_DTO);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateName(any());
        verify(fileDaoMock, never()).updateFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Update file incorrect file updating dto internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void updateFile_incorrectFileUpdatingDto_internalServerException() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        doNothing().when(fileValidatorMock).validateDescription(any());
        doNothing().when(fileValidatorMock).validateName(any());
        when(fileDaoMock.updateFile(any())).thenReturn(Boolean.FALSE);
        fileService.updateFile(CORRECT_FILE_UPDATING_DTO);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateDescription(any());
        verify(fileValidatorMock, times(TIMES_ONE)).validateName(any());
        verify(fileDaoMock, times(TIMES_ONE)).updateFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Delete file correct file id.
     */
    @Test
    public void deleteFile_correctFileId() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        when(fileDaoMock.getFileNameByFileId(any())).thenReturn(CORRECT_ENCODE_NAME);
        doNothing().when(fileWriterServiceMock).deleteFileByName(any());
        when(fileDaoMock.deleteFile(any())).thenReturn(Boolean.TRUE);
        fileService.deleteFile(CORRECT_ID);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileDaoMock, times(TIMES_ONE)).getFileNameByFileId(any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFileByName(any());
        verify(fileDaoMock, times(TIMES_ONE)).deleteFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Delete file incorrect file id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void deleteFile_incorrectFileId_validationException() {
        doThrow(ValidationException.class).when(fileValidatorMock).validateFileId(any());
        fileService.deleteFile(CORRECT_ID);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileDaoMock, never()).getFileNameByFileId(any());
        verify(fileWriterServiceMock, never()).deleteFileByName(any());
        verify(fileDaoMock, never()).deleteFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Delete file correct file id file not deleted exception.
     */
    @Test(expected = FileNotDeletedException.class)
    public void deleteFile_correctFileId_fileNotDeletedException() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        when(fileDaoMock.getFileNameByFileId(any())).thenReturn(CORRECT_ENCODE_NAME);
        doThrow(FileNotDeletedException.class).when(fileWriterServiceMock).deleteFileByName(any());
        fileService.deleteFile(CORRECT_ID);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileDaoMock, times(TIMES_ONE)).getFileNameByFileId(any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFileByName(any());
        verify(fileDaoMock, never()).deleteFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Delete file correct file id internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void deleteFile_correctFileId_internalServerException() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        when(fileDaoMock.getFileNameByFileId(any())).thenReturn(CORRECT_ENCODE_NAME);
        doNothing().when(fileWriterServiceMock).deleteFileByName(any());
        when(fileDaoMock.deleteFile(any())).thenReturn(Boolean.FALSE);
        fileService.deleteFile(CORRECT_ID);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(fileDaoMock, times(TIMES_ONE)).getFileNameByFileId(any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFileByName(any());
        verify(fileDaoMock, times(TIMES_ONE)).deleteFile(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Gets file count correct count returned.
     */
    @Test
    public void getFileCount_correctCountReturned() {
        when(fileDaoMock.getFileCount()).thenReturn((long) CORRECT_FILES.size());
        Long actualFileCount = fileService.getFileCount();
        Long expectedFileCount = (long) CORRECT_FILES.size();
        assertEquals(expectedFileCount, actualFileCount);
        verify(fileDaoMock, times(TIMES_ONE)).getFileCount();
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Gets file information by file id and authentication correct file id and authentication correct file updating dto returned.
     */
    @Test
    public void getFileInformationByFileIdAndAuthentication_correctFileIdAndAuthentication_correctFileUpdatingDtoReturned() {
        doNothing().when(fileValidatorMock).validateFileId(any());
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_USER_ID);
        when(fileDaoMock.getFileInformationByFileIdAndUserId(any(), any())).thenReturn(CORRECT_FILE_UPDATING_DTO);
        fileService.getFileInformationByFileIdAndAuthentication(CORRECT_ID, authenticationMock);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(fileDaoMock, times(TIMES_ONE)).getFileInformationByFileIdAndUserId(any(), any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Gets file information by file id and authentication incorrect file id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void getFileInformationByFileIdAndAuthentication_incorrectFileId_validationException() {
        doThrow(ValidationException.class).when(fileValidatorMock).validateFileId(any());
        fileService.getFileInformationByFileIdAndAuthentication(CORRECT_ID, authenticationMock);
        verify(fileValidatorMock, times(TIMES_ONE)).validateFileId(any());
        verify(commonServiceMock, never()).getUserIdByAuthentication(any());
        verify(fileDaoMock, never()).getFileInformationByFileIdAndUserId(any(), any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Gets file names by user id correct user id correct names returned.
     */
    @Test
    public void getFileNamesByUserId_correctUserId_correctNamesReturned() {
        when(fileDaoMock.getFileNamesByUserId(any())).thenReturn(any());
        fileService.getFileNamesByUserId(CORRECT_USER_ID);
        verify(fileDaoMock, times(TIMES_ONE)).getFileNamesByUserId(any());
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Build file download response.
     */
    @Test
    public void buildFileDownloadResponse() {
        MockHttpServletResponse actualResponse = new MockHttpServletResponse();
        fileService.buildFileDownloadResponse(actualResponse, CORRECT_REAL_NAME, CORRECT_FILE_LENGTH);
        assertEquals(actualResponse.getContentType(), fileService.getFileTypeByFileName(CORRECT_REAL_NAME));
        assertEquals(actualResponse.getHeader("Content-Disposition"), String.format("attachment; filename=\"%s\"", CORRECT_REAL_NAME));
        assertEquals(java.util.Optional.of(actualResponse.getContentLength()).get(), CORRECT_FILE_LENGTH);
        verifyNoMoreInteractions(
                fileValidatorMock,
                categoryServiceMock,
                fileWriterServiceMock,
                commonServiceMock,
                commonValidatorMock);
    }

    /**
     * Gets file type by file name known file type correct file type.
     */
    @Test
    public void getFileTypeByFileName_knownFileType_correctFileType() {
        mockStatic(URLConnection.class);
        PowerMockito.when(URLConnection.guessContentTypeFromName(any())).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        String actualFileTypeByFileName = fileService.getFileTypeByFileName(CORRECT_REAL_NAME);
        assertEquals(MediaType.APPLICATION_PDF_VALUE, actualFileTypeByFileName);
    }

    /**
     * Gets file type by file name unknown file type correct file type.
     */
    @Test
    public void getFileTypeByFileName_unknownFileType_correctFileType() {
        mockStatic(URLConnection.class);
        PowerMockito.when(URLConnection.guessContentTypeFromName(any())).thenReturn(null);
        String actualFileTypeByFileName = fileService.getFileTypeByFileName(CORRECT_ENCODE_NAME);
        assertEquals(MediaType.APPLICATION_OCTET_STREAM_VALUE, actualFileTypeByFileName);
    }

}
