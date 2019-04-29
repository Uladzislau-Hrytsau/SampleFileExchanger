package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dao.File;
import com.exchange.dao.Pagination;
import com.exchange.dao.exception.FileNotDeletedException;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type File rest controller mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileRestControllerMockTest {

    /**
     * The constant TIMES_ONE.
     */
    public static final int TIMES_ONE = 1;
    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final String ID_PARAM = "id";
    private static final String FILE_ID_PARAM = "fileId";
    private static final String FILE_NAME_PARAM = "fileName";
    private static final String FILES_URI = "/files";
    private static final Integer CORRECT_PAGE = 1;
    private static final Integer CORRECT_SIZE = 2;
    private static final Long CORRECT_COUNT = 2L;
    private static final Long CORRECT_ID = 1L;
    private static final String CORRECT_FILE_NAME = "name";
    private static final File CORRECT_FILE = new File(
            1L, 1L, 1L, "description",
            "realName", "encodeName", LocalDate.of(1000, 10, 10));
    private static final FileUpdatingDto CORRECT_FILE_UPDATING_DTO = new FileUpdatingDto(
            1L, "description", "realName", LocalDate.of(1000, 10, 10));
    private static final List<File> CORRECT_FILES = Arrays.asList(
            CORRECT_FILE,
            CORRECT_FILE);
    private static final Pagination CORRECT_PAGINATION = new Pagination(CORRECT_COUNT);
    private static final Response CORRECT_RESPONSE = new Response(
            CORRECT_FILES,
            CORRECT_PAGINATION);

    @Mock
    private FileService fileServiceMock;
    @Mock
    private Authentication authenticationMock;
    @InjectMocks
    private FileRestController fileRestController;
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    /**
     * Gets users by page and size with correct page and size then throw correct response returned.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUsersByPageAndSizeWithCorrectPageAndSizeThenThrowCorrectResponseReturned() throws Exception {
        given(fileServiceMock.getFilesAndCountByPageAndSize(any(Integer.class), any(Integer.class)))
                .willReturn(CORRECT_RESPONSE);
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(PAGE_PARAM, String.valueOf(CORRECT_PAGE))
                .param(SIZE_PARAM, String.valueOf(CORRECT_SIZE)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(CORRECT_RESPONSE)));
        verify(fileServiceMock, times(TIMES_ONE)).getFilesAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Gets users by page and size with incorrect page and size then throw validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUsersByPageAndSizeWithIncorrectPageAndSizeThenThrowValidationException() throws Exception {
        given(fileServiceMock.getFilesAndCountByPageAndSize(any(Integer.class), any(Integer.class)))
                .willThrow(ValidationException.class);
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(PAGE_PARAM, String.valueOf(CORRECT_PAGE))
                .param(SIZE_PARAM, String.valueOf(CORRECT_SIZE)))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, times(TIMES_ONE)).getFilesAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Gets users by page and size with non page and size then throw bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUsersByPageAndSizeWithNonPageAndSizeThenThrowBadRequest() throws Exception {
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, never()).getFilesAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Update file with correct file updating dto.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileWithCorrectFileUpdatingDto() throws Exception {
        doNothing().when(fileServiceMock).updateFile(any(FileUpdatingDto.class));
        mockMvc.perform(put(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_FILE_UPDATING_DTO)))
                .andExpect(status().isOk());
        verify(fileServiceMock, times(TIMES_ONE)).updateFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Update file with incorrect file updating dto then throw validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileWithIncorrectFileUpdatingDtoThenThrowValidationException() throws Exception {
        doThrow(ValidationException.class).when(fileServiceMock).updateFile(any(FileUpdatingDto.class));
        mockMvc.perform(put(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_FILE_UPDATING_DTO)))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, times(TIMES_ONE)).updateFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Update file with incorrect file updating dto then throw internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileWithIncorrectFileUpdatingDtoThenThrowInternalServerException() throws Exception {
        doThrow(InternalServerException.class).when(fileServiceMock).updateFile(any(FileUpdatingDto.class));
        mockMvc.perform(put(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_FILE_UPDATING_DTO)))
                .andExpect(status().isInternalServerError());
        verify(fileServiceMock, times(TIMES_ONE)).updateFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Update file with non file updating dto then throw bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileWithNonFileUpdatingDtoThenThrowBadRequest() throws Exception {
        mockMvc.perform(put(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, never()).updateFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Download file with correct file id and file name.
     *
     * @throws Exception the exception
     */
    @Test
    public void downloadFileWithCorrectFileIdAndFileName() throws Exception {
        doNothing().when(fileServiceMock).downloadFileByFileIdAndFileName(any(Long.class), any(String.class), any(HttpServletResponse.class));
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FILE_ID_PARAM, String.valueOf(CORRECT_ID))
                .param(FILE_NAME_PARAM, CORRECT_FILE_NAME))
                .andExpect(status().isOk());
        verify(fileServiceMock, times(TIMES_ONE)).downloadFileByFileIdAndFileName(any(), any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Download file with correct file id and file name then throw bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void downloadFileWithCorrectFileIdAndFileNameThenThrowBadRequest() throws Exception {
        doThrow(ValidationException.class).when(fileServiceMock).downloadFileByFileIdAndFileName(any(Long.class), any(String.class), any(HttpServletResponse.class));
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FILE_ID_PARAM, String.valueOf(CORRECT_ID))
                .param(FILE_NAME_PARAM, CORRECT_FILE_NAME))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, times(TIMES_ONE)).downloadFileByFileIdAndFileName(any(), any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Download file with incorrect file id and file name then throw internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void downloadFileWithIncorrectFileIdAndFileNameThenThrowInternalServerException() throws Exception {
        doThrow(InternalServerException.class)
                .when(fileServiceMock).downloadFileByFileIdAndFileName(
                any(Long.class), any(String.class), any(HttpServletResponse.class));
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FILE_ID_PARAM, String.valueOf(CORRECT_ID))
                .param(FILE_NAME_PARAM, CORRECT_FILE_NAME))
                .andExpect(status().isInternalServerError());
        verify(fileServiceMock, times(TIMES_ONE)).downloadFileByFileIdAndFileName(any(), any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Download file with non file id and file name then throw bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void downloadFileWithNonFileIdAndFileNameThenThrowBadRequest() throws Exception {
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, never()).downloadFileByFileIdAndFileName(any(), any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Delete file with correct id.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileWithCorrectId() throws Exception {
        doNothing().when(fileServiceMock).deleteFile(any(Long.class));
        mockMvc.perform(delete(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isOk());
        verify(fileServiceMock, times(TIMES_ONE)).deleteFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Delete file with incorrect id then throw validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileWithIncorrectIdThenThrowValidationException() throws Exception {
        doThrow(ValidationException.class).when(fileServiceMock).deleteFile(any(Long.class));
        mockMvc.perform(delete(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, times(TIMES_ONE)).deleteFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Delete file with incorrect id then throw file not deleted exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileWithIncorrectIdThenThrowFileNotDeletedException() throws Exception {
        doThrow(FileNotDeletedException.class).when(fileServiceMock).deleteFile(any(Long.class));
        mockMvc.perform(delete(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isInternalServerError());
        verify(fileServiceMock, times(TIMES_ONE)).deleteFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Delete file with incorrect id then throw internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileWithIncorrectIdThenThrowInternalServerException() throws Exception {
        doThrow(InternalServerException.class).when(fileServiceMock).deleteFile(any(Long.class));
        mockMvc.perform(delete(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isInternalServerError());
        verify(fileServiceMock, times(TIMES_ONE)).deleteFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Delete file with non id then throw internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileWithNonIdThenThrowInternalServerException() throws Exception {
        mockMvc.perform(delete(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, never()).deleteFile(any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Gets file information by file id with correct file id and authentication then correct file updating dto.
     *
     * @throws Exception the exception
     */
    @Test
    public void getFileInformationByFileIdWithCorrectFileIdAndAuthenticationThenCorrectFileUpdatingDto() throws Exception {
        given(fileServiceMock.getFileInformationByFileIdAndAuthentication(any(Long.class), any(Authentication.class)))
                .willReturn(CORRECT_FILE_UPDATING_DTO);
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FILE_ID_PARAM, String.valueOf(CORRECT_ID))
                .principal(authenticationMock))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(CORRECT_FILE_UPDATING_DTO)));
        verify(fileServiceMock, times(TIMES_ONE)).getFileInformationByFileIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Gets file information by file id with incorrect file id and authentication then throw validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void getFileInformationByFileIdWithIncorrectFileIdAndAuthenticationThenThrowValidationException() throws Exception {
        given(fileServiceMock.getFileInformationByFileIdAndAuthentication(any(Long.class), any(Authentication.class)))
                .willThrow(ValidationException.class);
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FILE_ID_PARAM, String.valueOf(CORRECT_ID))
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, times(TIMES_ONE)).getFileInformationByFileIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    /**
     * Gets file information by file id with non file id and authentication then throw validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void getFileInformationByFileIdWithNonFileIdAndAuthenticationThenThrowValidationException() throws Exception {
        mockMvc.perform(get(FILES_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, never()).getFileInformationByFileIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

}
