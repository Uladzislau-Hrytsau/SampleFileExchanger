package com.exchange.service;

import com.exchange.dao.CategoryDao;
import com.exchange.dao.FileDao;
import com.exchange.dao.FolderDao;
import com.exchange.dto.category.CategoryDto;
import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.dto.structure.StructureDto;
import com.exchange.exception.ValidationException;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.implementation.StructureServiceImpl;
import com.exchange.service.validation.FolderValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StructureServiceImplMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Long CORRECT_USER_ID = 1L;
    private static final Long CORRECT_CATEGORY_ID = 1L;
    private static final Long CORRECT_FOLDER_ID = 1L;
    private static final Long INCORRECT_FOLDER_ID = -1L;
    private static final String CORRECT_NAME = "correctName";
    private static final List<FileStructureDto> CORRECT_FILE_STRUCTURE_DTOS = Arrays.asList(
            new FileStructureDto(CORRECT_USER_ID, CORRECT_FOLDER_ID, CORRECT_NAME),
            new FileStructureDto(CORRECT_USER_ID, CORRECT_FOLDER_ID, CORRECT_NAME));
    private static final List<CategoryDto> CORRECT_CATEGORY_DTOS = Arrays.asList(
            new CategoryDto(CORRECT_CATEGORY_ID, CORRECT_NAME),
            new CategoryDto(CORRECT_CATEGORY_ID, CORRECT_NAME));
    private static final List<FolderStructureDto> CORRECT_FOLDER_STRUCTURE_DTOS = Arrays.asList(
            new FolderStructureDto(CORRECT_FOLDER_ID, CORRECT_NAME),
            new FolderStructureDto(CORRECT_FOLDER_ID, CORRECT_NAME));

    @Mock
    private FileDao fileDaoMock;
    @Mock
    private FolderDao folderDaoMock;
    @Mock
    private CategoryDao categoryDaoMock;
    @Mock
    private CommonService commonServiceMock;
    @Mock
    private FolderValidator folderValidatorMock;
    @Mock
    private Authentication authenticationMock;
    @InjectMocks
    private StructureServiceImpl structureService;

    @Test
    public void getStructureAndCategoriesByFolderIdAndAuthentication_correctFolderIdAndAuthentication_correctStructureDtoReturned() {
        doNothing().when(folderValidatorMock).validateFolderId(any());
        when(commonServiceMock.getUserIdByAuthentication(any())).thenReturn(CORRECT_USER_ID);
        when(fileDaoMock.getFilesByUserIdAndFolderId(any(), any())).thenReturn(CORRECT_FILE_STRUCTURE_DTOS);
        when(folderDaoMock.getFoldersByUserIdAndParentId(any(), any())).thenReturn(CORRECT_FOLDER_STRUCTURE_DTOS);
        when(categoryDaoMock.getCategoriesByUserId(any())).thenReturn(CORRECT_CATEGORY_DTOS);
        StructureDto actualStructureDto = structureService.getStructureAndCategoriesByFolderIdAndAuthentication(
                CORRECT_FOLDER_ID, authenticationMock);
        StructureDto expectedStructureDto = new StructureDto(
                CORRECT_FILE_STRUCTURE_DTOS, CORRECT_FOLDER_STRUCTURE_DTOS, CORRECT_CATEGORY_DTOS);
        Assert.assertEquals(expectedStructureDto, actualStructureDto);
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderId(any());
        verify(commonServiceMock, times(TIMES_ONE)).getUserIdByAuthentication(any());
        verify(fileDaoMock, times(TIMES_ONE)).getFilesByUserIdAndFolderId(any(), any());
        verify(categoryDaoMock, times(TIMES_ONE)).getCategoriesByUserId(any());
        verifyNoMoreInteractions(
                fileDaoMock, categoryDaoMock, commonServiceMock, folderValidatorMock, authenticationMock);
    }

    @Test(expected = ValidationException.class)
    public void getStructureAndCategoriesByFolderIdAndAuthentication_incorrectFolderId_validationException() {
        doThrow(ValidationException.class).when(folderValidatorMock).validateFolderId(any());
        structureService.getStructureAndCategoriesByFolderIdAndAuthentication(INCORRECT_FOLDER_ID, authenticationMock);
        verify(folderValidatorMock, times(TIMES_ONE)).validateFolderId(any());
        verify(commonServiceMock, never()).getUserIdByAuthentication(any());
        verify(fileDaoMock, never()).getFilesByUserIdAndFolderId(any(), any());
        verify(categoryDaoMock, never()).getCategoriesByUserId(any());
        verifyNoMoreInteractions(
                fileDaoMock, categoryDaoMock, commonServiceMock, folderValidatorMock, authenticationMock);
    }

}
