package com.exchange.service.validation.folder;

import com.exchange.dao.FolderDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.validation.CommonValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FolderValidatorMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Long CORRECT_FOLDER_ID = 1L;
    private static final Long INCORRECT_FOLDER_ID = -1L;
    private static final Long CORRECT_USER_ID = 1L;
    private static final Long INCORRECT_USER_ID = -1L;
    private static final String CORRECT_NAME = "CORRECT_NAME";
    private static final String EMPTY_NAME = "";

    @Mock
    private CommonValidator commonValidatorMock;
    @Mock
    private FolderDao folderDaoMock;
    @InjectMocks
    private FolderValidator folderValidator;

    @Test
    public void validateFolderByUserId_correctFolderIdAndUserId_correct() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderByUserId(CORRECT_FOLDER_ID, CORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateFolderByUserId_incorrectFolderIdAndCorrectUserId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderByUserId(INCORRECT_FOLDER_ID, anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, never()).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateFolderByUserId_correctFolderIdAndIncorrectUserId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.validateFolderByUserId(anyLong(), INCORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    @Test
    public void validateFolderId_correctFolderId_correct() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    @Test(expected = ValidationException.class)
    public void validateFolderId_correctFolderId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.validateFolderId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    @Test
    public void existsByUserId_correctFolderIdAndUserId_correct() {
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.existsByUserId(anyLong(), anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(folderDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void existsByUserId_correctFolderIdAndUserId_validationException() {
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.existsByUserId(anyLong(), anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(folderDaoMock);
    }

    @Test
    public void validateFolderName_correctName_correct() {
        folderValidator.validateFolderName(CORRECT_NAME);
    }

    @Test(expected = ValidationException.class)
    public void validateFolderName_incorrectName_validationException() {
        folderValidator.validateFolderName(EMPTY_NAME);
    }

    @Test(expected = ValidationException.class)
    public void validateFolderName_nullName_validationException() {
        folderValidator.validateFolderName(null);
    }

}
