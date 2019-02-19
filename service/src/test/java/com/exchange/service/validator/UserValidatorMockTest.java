package com.exchange.service.validator;

import com.exchange.dao.UserDao;
import com.exchange.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorMockTest {

    private static final int ONE = 1;

    private static final String CORRECT_LOGIN = "login";
    private static final String CORRECT_PASSWORD = "password";
    private static final String EMPTY = "";
    private static final String NULL = null;
    private static final boolean FALSE = false;

    @Mock
    private UserDao userDaoMock;
    @InjectMocks
    private UserValidator userValidator;

    @Test
    public void validateExistingLoginSuccess_1_MockTest() {
        when(userDaoMock.checkUserByLogin(any())).thenReturn(FALSE);
        userValidator.validateExistingLogin(CORRECT_LOGIN, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateExistingLoginUnSuccess_1_MockTest() {
        userValidator.validateExistingLogin(EMPTY, userDaoMock);
        verify(userDaoMock.checkUserByLogin(any()), never());
    }

    @Test(expected = ValidationException.class)
    public void validateExistingLoginUnSuccess_2_MockTest() {
        userValidator.validateExistingLogin(NULL, userDaoMock);
        verify(userDaoMock.checkUserByLogin(any()), never());
    }

    @Test(expected = ValidationException.class)
    public void validateExistingLoginUnSuccess_3_MockTest() {
        when(userDaoMock.checkUserByLogin(any())).thenReturn(true);
        userValidator.validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        verify(userDaoMock.checkUserByLogin(any()), times(ONE));
    }

    @Test
    public void validatePasswordSuccess_1_MockTest() {
        userValidator.validatePassword(CORRECT_PASSWORD);
    }

    @Test(expected = ValidationException.class)
    public void validatePasswordUnSuccess_1_MockTest() {
        userValidator.validatePassword(EMPTY);
    }

    @Test(expected = ValidationException.class)
    public void validatePasswordUnSuccess_2_MockTest() {
        userValidator.validatePassword(NULL);
    }

}
