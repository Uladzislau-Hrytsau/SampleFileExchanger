package com.exchange.service.validation;

import com.exchange.dao.FolderDao;
import com.exchange.exception.ValidationException;
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
     * Validate folder by user id with correct folder id and user id then correct.
     */
    @Test
    public void validateFolderByUserIdWithCorrectFolderIdAndUserIdThenCorrect() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderByUserId(CORRECT_FOLDER_ID, CORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    /**
     * Validate folder by user id with incorrect folder id and correct user id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderByUserIdWithIncorrectFolderIdAndCorrectUserIdThenThrowValidationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderByUserId(INCORRECT_FOLDER_ID, anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, never()).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    /**
     * Validate folder by user id with correct folder id and incorrect user id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderByUserIdWithCorrectFolderIdAndIncorrectUserIdThenThrowValidationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.validateFolderByUserId(anyLong(), INCORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(commonValidatorMock, folderDaoMock);
    }

    /**
     * Validate folder id with correct folder id then correct.
     */
    @Test
    public void validateFolderIdWithCorrectFolderIdThenCorrect() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.validateFolderId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate folder id with correct folder id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderIdWithCorrectFolderIdThenThrowValidationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.validateFolderId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Exists by user id with correct folder id and user id then correct.
     */
    @Test
    public void existsByUserIdWithCorrectFolderIdAndUserIdThenCorrect() {
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);
        folderValidator.existsByUserId(anyLong(), anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(folderDaoMock);
    }

    /**
     * Exists by user id with correct folder id and user id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void existsByUserIdWithCorrectFolderIdAndUserIdThenThrowValidationException() {
        when(folderDaoMock.existsParentIdByUserId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);
        folderValidator.existsByUserId(anyLong(), anyLong());
        verify(folderDaoMock, times(TIMES_ONE)).existsParentIdByUserId(anyLong(), anyLong());
        verifyNoMoreInteractions(folderDaoMock);
    }

    /**
     * Validate folder name with correct name then correct.
     */
    @Test
    public void validateFolderNameWithCorrectNameThenCorrect() {
        folderValidator.validateFolderName(CORRECT_NAME);
    }

    /**
     * Validate folder name with incorrect name then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderNameWithIncorrectNameThenThrowValidationException() {
        folderValidator.validateFolderName(EMPTY_NAME);
    }

    /**
     * Validate folder name with null name then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFolderNameWithNullNameThenThrowValidationException() {
        folderValidator.validateFolderName(null);
    }

}
