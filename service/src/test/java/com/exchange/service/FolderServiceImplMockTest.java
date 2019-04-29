package com.exchange.service;

import com.exchange.dao.FileDao;
import com.exchange.dao.FolderDao;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.implementation.FolderServiceImpl;
import com.exchange.service.validation.CommonValidator;
import com.exchange.service.validation.FolderValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.mockito.Mockito.*;

/**
 * The type Folder service impl mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class FolderServiceImplMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Long CORRECT_IDENTIFIER = 1L;
    private static final String CORRECT_STRING = "CORRECT_STRING";
    private static final FolderStructureDto CORRECT_FOLDER_STRUCTURE_DTO = new FolderStructureDto(
            CORRECT_IDENTIFIER,
            CORRECT_STRING);

    @Mock
    private FolderDao folderDaoMock;
    @Mock
    private FileWriterService fileWriterServiceMock;
    @Mock
    private FileDao fileDaoMock;
    @Mock
    private FolderValidator folderValidatorMock;
    @Mock
    private CommonService commonServiceMock;
    @Mock
    private CommonValidator commonValidatorMock;
    @Mock
    private OAuth2Authentication authentication;
    @InjectMocks
    private FolderServiceImpl folderService;

    /**
     * Add folder with correct folder id then correct.
     */
    @Test
    public void addFolderWithCorrectFolderIdThenCorrect() {
        doNothing().when(folderValidatorMock).validateFolderName(anyString());
        when(folderDaoMock.addFolder(any(), any())).thenReturn(anyLong());
        when(commonValidatorMock.isValidIdentifier(CORRECT_IDENTIFIER)).thenReturn(Boolean.TRUE);
        folderService.addFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderName(any());
        verify(folderDaoMock, times(TIMES_ONE)).addFolder(any(), any());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(any());
        verifyNoMoreInteractions(folderDaoMock, fileWriterServiceMock, fileDaoMock, folderValidatorMock,
                commonValidatorMock, authentication);
    }

    /**
     * Add folder with incorrect folder id then throw internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void addFolderWithIncorrectFolderIdThenThrowInternalServerException() {
        doNothing().when(folderValidatorMock).validateFolderName(anyString());
        when(folderDaoMock.addFolder(any(), any())).thenReturn(anyLong());
        when(commonValidatorMock.isValidIdentifier(CORRECT_IDENTIFIER)).thenReturn(Boolean.FALSE);
        folderService.addFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderName(any());
        verify(folderDaoMock, times(TIMES_ONE)).addFolder(any(), any());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(any());
        verifyNoMoreInteractions(folderDaoMock, fileWriterServiceMock, fileDaoMock, folderValidatorMock,
                commonServiceMock, commonValidatorMock, authentication);
    }

    /**
     * Delete by folder id and authentication with correct folder id then correct.
     */
    @Test
    public void deleteByFolderIdAndAuthenticationWithCorrectFolderIdThenCorrect() {
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_IDENTIFIER);
        doNothing().when(folderValidatorMock).validateFolderByUserId(CORRECT_IDENTIFIER, CORRECT_IDENTIFIER);
        doNothing().when(fileWriterServiceMock).deleteFilesByNames(any());
        when(folderDaoMock.deleteByFolderIdAndUserId(any(), any())).thenReturn(Boolean.TRUE);
        folderService.deleteByFolderIdAndAuthentication(CORRECT_IDENTIFIER, authentication);
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderByUserId(any(), any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFilesByNames(any());
        verifyNoMoreInteractions(fileWriterServiceMock, folderValidatorMock,
                commonServiceMock, commonValidatorMock, authentication);
    }

    /**
     * Delete by folder id and authentication with incorrect folder id then throw internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void deleteByFolderIdAndAuthenticationWithIncorrectFolderIdThenThrowInternalServerException() {
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_IDENTIFIER);
        doNothing().when(folderValidatorMock).validateFolderByUserId(CORRECT_IDENTIFIER, CORRECT_IDENTIFIER);
        doNothing().when(fileWriterServiceMock).deleteFilesByNames(any());
        when(folderDaoMock.deleteByFolderIdAndUserId(any(), any())).thenReturn(Boolean.FALSE);
        folderService.deleteByFolderIdAndAuthentication(CORRECT_IDENTIFIER, authentication);
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderByUserId(any(), any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFilesByNames(any());
        verify(folderDaoMock, times(TIMES_ONE)).deleteByFolderIdAndUserId(any(), any());
        verifyNoMoreInteractions(folderDaoMock, fileWriterServiceMock, fileDaoMock, folderValidatorMock,
                commonServiceMock, commonValidatorMock, authentication);
    }

    /**
     * Update folder with correct folder structure dto then correct.
     */
    @Test
    public void updateFolderWithCorrectFolderStructureDtoThenCorrect() {
        doNothing().when(folderValidatorMock).validateFolderName(any());
        doNothing().when(folderValidatorMock).validateFolderId(any());
        when(commonServiceMock.getUserIdByAuthentication(authentication)).thenReturn(CORRECT_IDENTIFIER);
        when(folderDaoMock.updateFolderNameByFolderIdAndUserId(CORRECT_FOLDER_STRUCTURE_DTO, CORRECT_IDENTIFIER)).thenReturn(Boolean.TRUE);
        folderService.updateFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderName(any());
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderId(any());
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(folderDaoMock, times(TIMES_ONE)).updateFolderNameByFolderIdAndUserId(any(), any());
        verifyNoMoreInteractions(folderDaoMock, fileWriterServiceMock, fileDaoMock, folderValidatorMock,
                commonServiceMock, commonValidatorMock, authentication);
    }

    /**
     * Update folder with incorrect folder structure dto then throw internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void updateFolderWithIncorrectFolderStructureDtoThenThrowInternalServerException() {
        doNothing().when(folderValidatorMock).validateFolderName(any());
        doNothing().when(folderValidatorMock).validateFolderId(any());
        when(commonServiceMock.getUserIdByAuthentication(authentication)).thenReturn(CORRECT_IDENTIFIER);
        when(folderDaoMock.updateFolderNameByFolderIdAndUserId(CORRECT_FOLDER_STRUCTURE_DTO, CORRECT_IDENTIFIER)).thenReturn(Boolean.FALSE);
        folderService.updateFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderName(any());
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderId(any());
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(folderDaoMock, times(TIMES_ONE)).updateFolderNameByFolderIdAndUserId(any(), any());
        verifyNoMoreInteractions(folderDaoMock, fileWriterServiceMock, fileDaoMock, folderValidatorMock,
                commonServiceMock, commonValidatorMock, authentication);
    }
}
