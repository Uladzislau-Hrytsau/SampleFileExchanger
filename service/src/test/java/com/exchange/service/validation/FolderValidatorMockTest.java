package com.exchange.service.validation;

import com.exchange.dao.FolderDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.validation.CommonValidator;
import com.exchange.service.validation.FolderValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * The type Folder validator mock test.
 */
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

    /**
     * Validate folder by user id correct folder id and user id correct.
     */
    @Test
    public void validateFolderByUserId_correctFolderIdAndUserId_correct() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderByUserId(CORRECT_FOLDER_ID, CORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    /**
     * Validate folder by user id incorrect folder id and correct user id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderByUserId_incorrectFolderIdAndCorrectUserId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderByUserId(INCORRECT_FOLDER_ID, anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, never()).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    /**
     * Validate folder by user id correct folder id and incorrect user id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderByUserId_correctFolderIdAndIncorrectUserId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.validateFolderByUserId(anyLong(), INCORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    /**
     * Validate folder id correct folder id correct.
     */
    @Test
    public void validateFolderId_correctFolderId_correct() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate folder id correct folder id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderId_correctFolderId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.validateFolderId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Exists by user id correct folder id and user id correct.
     */
    @Test
    public void existsByUserId_correctFolderIdAndUserId_correct() {
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.existsByUserId(anyLong(), anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(folderDaoMock);
    }

    /**
     * Exists by user id correct folder id and user id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void existsByUserId_correctFolderIdAndUserId_validationException() {
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.existsByUserId(anyLong(), anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(folderDaoMock);
    }

    /**
     * Validate folder name correct name correct.
     */
    @Test
    public void validateFolderName_correctName_correct() {
        folderValidator.validateFolderName(CORRECT_NAME);
    }

    /**
     * Validate folder name incorrect name validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderName_incorrectName_validationException() {
        folderValidator.validateFolderName(EMPTY_NAME);
    }

    /**
     * Validate folder name null name validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderName_nullName_validationException() {
        folderValidator.validateFolderName(null);
    }

}
