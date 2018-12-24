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

@RunWith(MockitoJUnitRunner.class)
public class ValidatorMockTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMPTY = "";
    private static final String NULL = null;
    @Mock
    private UserDao userDaoMock;
    @Mock
    private User userMock;
    @InjectMocks
    private Validator validator;

    @Test
    public void validateLoginAndPasswordSuccessMockTest() {
        when(userMock.getLogin()).thenReturn(LOGIN);
        when(userMock.getPassword()).thenReturn(PASSWORD);
        doReturn(false).when(userDaoMock).checkUserByLogin(anyString());
        validator.validateLoginAndPassword(userMock, userDaoMock);

    }

    @Test(expected = ValidationException.class)
    public void validateLoginAndPasswordUnSuccess_1_MockTest() {
        when(userMock.getLogin()).thenReturn(EMPTY);
        when(userMock.getPassword()).thenReturn(EMPTY);
        verify(userDaoMock, times(0)).checkUserByLogin(anyString());
        validator.validateLoginAndPassword(userMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateLoginAndPasswordUnSuccess_2_MockTest() {
        when(userMock.getLogin()).thenReturn(NULL);
        when(userMock.getPassword()).thenReturn(NULL);
        verify(userDaoMock, times(0)).checkUserByLogin(anyString());
        validator.validateLoginAndPassword(userMock, userDaoMock);
    }

    @Test(expected = ValidationException.class)
    public void validateLoginAndPasswordUnSuccess_3_MockTest() {
        when(userMock.getLogin()).thenReturn(LOGIN);
        when(userMock.getPassword()).thenReturn(PASSWORD);
        doThrow(ValidationException.class).when(userDaoMock).checkUserByLogin(anyString());
        validator.validateLoginAndPassword(userMock, userDaoMock);
    }

}
