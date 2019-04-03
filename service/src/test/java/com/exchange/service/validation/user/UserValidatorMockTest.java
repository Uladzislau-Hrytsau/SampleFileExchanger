package com.exchange.service.validation.user;

import com.exchange.dao.UserDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.validation.CommonValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Long CORRECT_USER_ID = 1L;
    private static final Long INCORRECT_USER_ID = -1L;
    private static final String CORRECT_LOGIN = "CORRECT_LOGIN";
    private static final String EMPTY_LOGIN = "";
    private static final String CORRECT_PASSWORD = "CORRECT_PASSWORD";
    private static final String EMPTY_PASSWORD = "EMPTY_PASSWORD";
    private static final String CORRECT_INFORMATION = "CORRECT_INFORMATION";
    private static final String INCORRECT_INFORMATION = "INCORRECT_INFORMATION";

    @Mock
    private CommonValidator commonValidatorMock;
    @Mock
    private UserDao userDaoMock;
    @InjectMocks
    private UserValidator userValidator;

    @Test
    public void validateUserId_correctUserId_correct() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.TRUE);
        userValidator.validateUserId(CORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateUserId_incorrectUserId_validationException() {
        when(commonValidatorMock.isValidIdentifier(anyLong())).thenReturn(Boolean.FALSE);
        userValidator.validateUserId(INCORRECT_USER_ID);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(anyLong());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test
    public void validateExistingLogin_correctLoginAndUserDao_correct() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.TRUE);
        when(userDaoMock.checkUserByLogin(anyString())).thenReturn(Boolean.FALSE);
        userValidator.validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verify(userDaoMock, times(TIMES_ONE)).checkUserByLogin(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateExistingLogin_incorrectLoginAndCorrectUserDao_validationException() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.FALSE);
        userValidator.validateExistingLogin(EMPTY_LOGIN, userDaoMock);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verify(userDaoMock, never()).checkUserByLogin(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateExistingLogin_correctLoginAndIncorrectUserDao_validationException() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.FALSE);
        when(userDaoMock.checkUserByLogin(anyString())).thenReturn(Boolean.TRUE);
        userValidator.validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verify(userDaoMock, times(TIMES_ONE)).checkUserByLogin(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test
    public void validatePassword_correctPassword_correct() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.TRUE);
        userValidator.validatePassword(CORRECT_PASSWORD);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validatePassword_incorrectPassword_validationException() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.FALSE);
        userValidator.validatePassword(EMPTY_PASSWORD);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test
    public void validateInformation_correctInformation_correct() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.TRUE);
        userValidator.validatePassword(CORRECT_INFORMATION);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateInformation_incorrectInformation_correct() {
        when(commonValidatorMock.isValidString(anyString())).thenReturn(Boolean.FALSE);
        userValidator.validatePassword(INCORRECT_INFORMATION);
        verify(commonValidatorMock, times(TIMES_ONE)).isValidString(anyString());
        verifyNoMoreInteractions(commonValidatorMock, userDaoMock);
    }

}
