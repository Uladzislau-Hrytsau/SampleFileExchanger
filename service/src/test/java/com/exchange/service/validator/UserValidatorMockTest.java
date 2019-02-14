package com.exchange.service.validator;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * The type User validator mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserValidatorMockTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMPTY = "";
    private static final String NULL = null;
    @Mock
    private UserDao userDaoMock;
    @Mock
    private User userMock;
    @InjectMocks
    private UserValidator userValidator;

    /**
     * Validate login and password success 1 mock test.
     */
    @Test
    public void validateLoginAndPasswordSuccess_1_MockTest() {
        when(userMock.getLogin()).thenReturn(LOGIN);
        when(userMock.getPassword()).thenReturn(PASSWORD);
        userValidator.validateLoginAndPassword(userMock);
    }

    /**
     * Validate login and password unsuccess 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void validateLoginAndPasswordUnsuccess_1_MockTest() {
        when(userMock.getLogin()).thenReturn(EMPTY);
        when(userMock.getPassword()).thenReturn(EMPTY);
        userValidator.validateLoginAndPassword(userMock);
    }

    /**
     * Validate login and password unsuccess 2 mock test.
     */
    @Test(expected = ValidationException.class)
    public void validateLoginAndPasswordUnsuccess_2_MockTest() {
        when(userMock.getLogin()).thenReturn(NULL);
        when(userMock.getPassword()).thenReturn(NULL);
        userValidator.validateLoginAndPassword(userMock);
    }

    /**
     * Validate existing login success 1 mock test.
     */
    @Test
    public void validateExistingLoginSuccess_1_MockTest() {
        when(userDaoMock.checkUserByLogin(LOGIN)).thenReturn(false);
        userValidator.validateExistingLogin(LOGIN, userDaoMock);
    }

    /**
     * Validate existing login unseccess 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void validateExistingLoginUnseccess_1_MockTest() {
        doThrow(ValidationException.class).when(userDaoMock).checkUserByLogin(anyString());
        userValidator.validateExistingLogin(LOGIN, userDaoMock);
    }

}
