package com.exchange.service;

import com.exchange.dao.FileDao;
import com.exchange.dao.FolderDao;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.implementation.FolderServiceImpl;
import com.exchange.service.validation.CommonValidator;
import com.exchange.service.validation.folder.FolderValidator;
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

    private static final Long CORRECT_IDENTIFIER = 1L;
    private static final String CORRECT_STRING = "CORRECT_STRING";
    private static final FolderStructureDto CORRECT_FOLDER_STRUCTURE_DTO = new FolderStructureDto(
            CORRECT_IDENTIFIER,
            CORRECT_STRING
    );

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
     * Add folder correct folder id correct.
     */
    @Test
    public void addFolder_correctFolderId_correct() {
        doNothing().when(folderValidatorMock).validateFolderName(anyString());
        when(folderDaoMock.addFolder(any(), any())).thenReturn(anyLong());
        when(commonValidatorMock.isValidIdentifier(CORRECT_IDENTIFIER)).thenReturn(Boolean.TRUE);
        folderService.addFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
    }

    /**
     * Add folder incorrect folder id internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void addFolder_incorrectFolderId_internalServerException() {
        doNothing().when(folderValidatorMock).validateFolderName(anyString());
        when(folderDaoMock.addFolder(any(), any())).thenReturn(anyLong());
        when(commonValidatorMock.isValidIdentifier(CORRECT_IDENTIFIER)).thenReturn(Boolean.FALSE);
        folderService.addFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
    }

    /**
     * Delete by folder id and authentication correct folder id correct.
     */
    @Test
    public void deleteByFolderIdAndAuthentication_correctFolderId_correct() {
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_IDENTIFIER);
        doNothing().when(folderValidatorMock).validateFolderByUserId(CORRECT_IDENTIFIER, CORRECT_IDENTIFIER);
        doNothing().when(fileWriterServiceMock).deleteFilesByNames(any());
        when(folderDaoMock.deleteByFolderIdAndUserId(any(), any())).thenReturn(Boolean.TRUE);
        folderService.deleteByFolderIdAndAuthentication(CORRECT_IDENTIFIER, authentication);
    }

    /**
     * Delete by folder id and authentication incorrect folder id internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void deleteByFolderIdAndAuthentication_incorrectFolderId_internalServerException() {
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_IDENTIFIER);
        doNothing().when(folderValidatorMock).validateFolderByUserId(CORRECT_IDENTIFIER, CORRECT_IDENTIFIER);
        doNothing().when(fileWriterServiceMock).deleteFilesByNames(any());
        when(folderDaoMock.deleteByFolderIdAndUserId(any(), any())).thenReturn(Boolean.FALSE);
        folderService.deleteByFolderIdAndAuthentication(CORRECT_IDENTIFIER, authentication);
    }

    /**
     * Update folder correct folder structure dto correct.
     */
    @Test
    public void updateFolder_correctFolderStructureDto_correct() {
        doNothing().when(folderValidatorMock).validateFolderName(any());
        doNothing().when(folderValidatorMock).validateFolderId(any());
        when(commonServiceMock.getUserIdByAuthentication(authentication)).thenReturn(CORRECT_IDENTIFIER);
        when(folderDaoMock.updateFolderNameByFolderIdAndUserId(CORRECT_FOLDER_STRUCTURE_DTO, CORRECT_IDENTIFIER)).thenReturn(Boolean.TRUE);
        folderService.updateFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
    }

    /**
     * Update folder incorrect folder structure dto internal server exception.
     */
    @Test(expected = InternalServerException.class)
    public void updateFolder_incorrectFolderStructureDto_internalServerException() {
        doNothing().when(folderValidatorMock).validateFolderName(any());
        doNothing().when(folderValidatorMock).validateFolderId(any());
        when(commonServiceMock.getUserIdByAuthentication(authentication)).thenReturn(CORRECT_IDENTIFIER);
        when(folderDaoMock.updateFolderNameByFolderIdAndUserId(CORRECT_FOLDER_STRUCTURE_DTO, CORRECT_IDENTIFIER)).thenReturn(Boolean.FALSE);
        folderService.updateFolder(CORRECT_FOLDER_STRUCTURE_DTO, authentication);
    }
}
