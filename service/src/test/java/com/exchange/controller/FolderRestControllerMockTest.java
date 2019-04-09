package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dao.exception.FileNotDeletedException;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.FolderService;
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

import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Folder rest controller mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class FolderRestControllerMockTest {

    /**
     * The constant TIMES_ONE.
     */
    public static final int TIMES_ONE = 1;
    private static final String FOLDER_ID = "folderId";
    private static final String FOLDERS_URI = "/folders";
    private static final String CORRECT_FOLDER_ID = "1";
    private static final FolderStructureDto FOLDER_STRUCTURE_DTO = new FolderStructureDto(
            1L, "name");
    @Mock
    private FolderService folderServiceMock;
    @Mock
    private Authentication authenticationMock;
    @InjectMocks
    private FolderRestController folderRestController;
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(folderRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    /**
     * Add folder correct folder structure dto and authentication.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFolder_correctFolderStructureDtoAndAuthentication() throws Exception {
        doNothing().when(folderServiceMock).addFolder(any(FolderStructureDto.class), any(Authentication.class));
        mockMvc.perform(post(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(FOLDER_STRUCTURE_DTO))
                .principal(authenticationMock))
                .andExpect(status().isCreated());
        verify(folderServiceMock, times(TIMES_ONE)).addFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Add folder validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFolder_validationException() throws Exception {
        doThrow(ValidationException.class).when(folderServiceMock).addFolder(any(), any());
        mockMvc.perform(post(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(FOLDER_STRUCTURE_DTO))
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(folderServiceMock, times(TIMES_ONE)).addFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Add folder internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFolder_internalServerException() throws Exception {
        doThrow(InternalServerException.class).when(folderServiceMock).addFolder(any(), any());
        mockMvc.perform(post(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(FOLDER_STRUCTURE_DTO))
                .principal(authenticationMock))
                .andExpect(status().isInternalServerError());
        verify(folderServiceMock, times(TIMES_ONE)).addFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Add folder bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFolder_badRequest() throws Exception {
        mockMvc.perform(post(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .principal(authenticationMock));
        verify(folderServiceMock, never()).addFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Update folder name correct folder structure dto and authentication.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFolderName_correctFolderStructureDtoAndAuthentication() throws Exception {
        doNothing().when(folderServiceMock).updateFolder(any(), any());
        mockMvc.perform(put(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(FOLDER_STRUCTURE_DTO))
                .principal(authenticationMock))
                .andExpect(status().isOk());
        verify(folderServiceMock, times(TIMES_ONE)).updateFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Update folder name validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFolderName_validationException() throws Exception {
        doThrow(ValidationException.class).when(folderServiceMock).updateFolder(any(), any());
        mockMvc.perform(put(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(FOLDER_STRUCTURE_DTO))
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(folderServiceMock, times(TIMES_ONE)).updateFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Update folder name internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFolderName_internalServerException() throws Exception {
        doThrow(InternalServerException.class).when(folderServiceMock).updateFolder(any(), any());
        mockMvc.perform(put(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(FOLDER_STRUCTURE_DTO))
                .principal(authenticationMock))
                .andExpect(status().isInternalServerError());
        verify(folderServiceMock, times(TIMES_ONE)).updateFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Update folder name non folder structure dto and authentication bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFolderName_nonFolderStructureDtoAndAuthentication_badRequest() throws Exception {
        mockMvc.perform(put(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(folderServiceMock, never()).updateFolder(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Delete folder correct folder id and authentication.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFolder_correctFolderIdAndAuthentication() throws Exception {
        doNothing().when(folderServiceMock).deleteByFolderIdAndAuthentication(any(Long.class), any(Authentication.class));
        mockMvc.perform(delete(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID, CORRECT_FOLDER_ID)
                .principal(authenticationMock))
                .andExpect(status().isOk());
        verify(folderServiceMock, times(TIMES_ONE)).deleteByFolderIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Delete folder validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFolder_validationException() throws Exception {
        doThrow(ValidationException.class).when(folderServiceMock).deleteByFolderIdAndAuthentication(any(Long.class), any(Authentication.class));
        mockMvc.perform(delete(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID, CORRECT_FOLDER_ID)
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(folderServiceMock, times(TIMES_ONE)).deleteByFolderIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Delete folder file not deleted exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFolder_fileNotDeletedException() throws Exception {
        doThrow(FileNotDeletedException.class).when(folderServiceMock).deleteByFolderIdAndAuthentication(any(Long.class), any(Authentication.class));
        mockMvc.perform(delete(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID, CORRECT_FOLDER_ID)
                .principal(authenticationMock))
                .andExpect(status().isInternalServerError());
        verify(folderServiceMock, times(TIMES_ONE)).deleteByFolderIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Delete folder internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFolder_internalServerException() throws Exception {
        doThrow(InternalServerException.class).when(folderServiceMock).deleteByFolderIdAndAuthentication(any(Long.class), any(Authentication.class));
        mockMvc.perform(delete(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID, CORRECT_FOLDER_ID)
                .principal(authenticationMock))
                .andExpect(status().isInternalServerError());
        verify(folderServiceMock, times(TIMES_ONE)).deleteByFolderIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

    /**
     * Delete folder non folder id and authentication bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFolder_nonFolderIdAndAuthentication_badRequest() throws Exception {
        doThrow(InternalServerException.class).when(folderServiceMock).deleteByFolderIdAndAuthentication(any(Long.class), any(Authentication.class));
        mockMvc.perform(delete(FOLDERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID, CORRECT_FOLDER_ID)
                .principal(authenticationMock))
                .andExpect(status().isInternalServerError());
        verify(folderServiceMock, times(TIMES_ONE)).deleteByFolderIdAndAuthentication(any(), any());
        verifyNoMoreInteractions(folderServiceMock);
    }

}
