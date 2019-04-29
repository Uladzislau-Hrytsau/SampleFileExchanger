package com.exchange.service.validation;

import com.exchange.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * The type File validator mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileValidatorMockTest {

    private static final Integer TIMES_ONE = 1;

    @Mock
    private CommonValidator commonValidatorMock;
    @InjectMocks
    private FileValidator fileValidator;

    /**
     * Validate file id with correct file id then correct.
     */
    @Test
    public void validateFileIdWithCorrectFileIdThenCorrect() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        fileValidator.validateFileId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate file id with incorrect file id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateFileIdWithIncorrectFileIdThenThrowValidationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        fileValidator.validateFileId(anyLong());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate description with correct description then correct.
     */
    @Test
    public void validateDescriptionWithCorrectDescriptionThenCorrect() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.TRUE);
        fileValidator.validateDescription(anyString());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate description with incorrect description then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateDescriptionWithIncorrectDescriptionThenThrowValidationException() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.FALSE);
        fileValidator.validateDescription(anyString());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate name with correct name then correct.
     */
    @Test
    public void validateNameWithCorrectNameThenCorrect() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.TRUE);
        fileValidator.validateName(anyString());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock);
    }

    /**
     * Validate name with incorrect name then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateNameWithIncorrectNameThenThrowValidationException() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.FALSE);
        fileValidator.validateName(anyString());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock);
    }

}
