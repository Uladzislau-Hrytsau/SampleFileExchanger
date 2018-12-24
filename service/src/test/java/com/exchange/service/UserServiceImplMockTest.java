package com.exchange.service;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.validator.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplMockTest {

    private static final Long CORRECT_ID = 1L;
    private static final Long NULL_ID = null;
    private static final Long INCORRECT_ID = -1L;
    private static final String CORRECT_LOGIN = "login";
    private static final String NULL_LOGIN = null;
    private static final String EMPTY_LOGIN = "";

    private static final ValidationException validationException = new ValidationException();
    private static final InternalServerException internalServerException = new InternalServerException();
    @Mock
    private UserDao userDaoMock;
    @Mock
    private Validator validatorMock;
    @Mock
    private User userMock;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void getUserByUserIdSuccess_1_MockTest() {
        when(userDaoMock.checkUserByUserId(anyLong())).thenReturn(true);
        userServiceImpl.getUserByUserId(CORRECT_ID);
        verify(userDaoMock, times(1)).getUserByUserId(anyLong());
    }

    @Test(expected = ValidationException.class)
    public void getUserByUserIdUnSuccess_1_MockTest() {
        userServiceImpl.getUserByUserId(NULL_ID);
        verify(userDaoMock, never()).checkUserByUserId(anyLong());
        verify(userDaoMock, never()).getUserByUserId(anyLong());
    }

    @Test(expected = ValidationException.class)
    public void getUserByUserIdUnSuccess_2_MockTest() {
        userServiceImpl.getUserByUserId(INCORRECT_ID);
        verify(userDaoMock, never()).checkUserByUserId(anyLong());
        verify(userDaoMock, never()).getUserByUserId(anyLong());
    }

    @Test(expected = ValidationException.class)
    public void getUserByUserIdUnSuccess_3_MockTest() {
        when(userDaoMock.checkUserByUserId(anyLong())).thenReturn(false);
        userServiceImpl.getUserByUserId(CORRECT_ID);
        verify(userDaoMock, never()).getUserByUserId(anyLong());
    }


    @Test
    public void getUserByLoginSuccess_1_MockTest() {
        when(userDaoMock.checkUserByLogin(anyString())).thenReturn(true);
        userServiceImpl.getUserByLogin(CORRECT_LOGIN);
        verify(userDaoMock, times(1)).getUserByLogin(anyString());
    }

    @Test(expected = ValidationException.class)
    public void getUserByLoginUnSuccess_1_MockTest() {
        userServiceImpl.getUserByLogin(NULL_LOGIN);
        verify(userDaoMock, never()).checkUserByLogin(anyString());
        verify(userDaoMock, never()).getUserByLogin(anyString());
    }

    @Test(expected = ValidationException.class)
    public void getUserByLoginUnSuccess_2_MockTest() {
        userServiceImpl.getUserByLogin(EMPTY_LOGIN);
        verify(userDaoMock, never()).checkUserByLogin(anyString());
        verify(userDaoMock, never()).getUserByLogin(anyString());
    }

    @Test(expected = ValidationException.class)
    public void getUserByLoginUnSuccess_3_MockTest() {
        when(userDaoMock.checkUserByLogin(anyString())).thenReturn(false);
        userServiceImpl.getUserByLogin(CORRECT_LOGIN);
        verify(userDaoMock, never()).getUserByLogin(anyString());
    }


    @Test
    public void addUserSuccess_1_MockTest() {
        doNothing().when(validatorMock).validateLoginAndPassword(userMock, userDaoMock);
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, times(1)).addUser(userMock);
    }

    @Test(expected = ValidationException.class)
    public void addUserUnSuccess_1_MockTest() {
        doThrow(ValidationException.class).when(validatorMock).validateLoginAndPassword(userMock, userDaoMock);
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, never()).addUser(any(User.class));
    }


    @Test
    public void updateUserSuccess_1_MockTest() {
        doNothing().when(validatorMock).validateLoginAndPassword(userMock, userDaoMock);
        when(userDaoMock.updateUser(userMock)).thenReturn(1);
        userServiceImpl.updateUser(userMock);
    }

    @Test(expected = ValidationException.class)
    public void updateUserUnSuccess_1_MockTest() {
        doThrow(ValidationException.class).when(validatorMock).validateLoginAndPassword(userMock, userDaoMock);
        userServiceImpl.updateUser(userMock);
        verify(userDaoMock, never()).updateUser(any(User.class));
    }

    @Test(expected = InternalServerException.class)
    public void updateUserUnSuccess_2_MockTest() {
        doNothing().when(validatorMock).validateLoginAndPassword(any(User.class), any(UserDao.class));
        when(userDaoMock.updateUser(any(User.class))).thenReturn(0);
        userServiceImpl.updateUser(userMock);
    }


    @Test
    public void deleteUserSuccess_1_MockTest() {
        when(userDaoMock.deleteUser(CORRECT_ID)).thenReturn(1);
        userServiceImpl.deleteUser(CORRECT_ID);
    }

    @Test(expected = ValidationException.class)
    public void deleteUserUnSuccess_1_MockTest() {
        userServiceImpl.deleteUser(INCORRECT_ID);
        verify(userDaoMock, never()).deleteUser(INCORRECT_ID);
    }

    @Test(expected = ValidationException.class)
    public void deleteUserUnSuccess_2_MockTest() {
        userServiceImpl.deleteUser(NULL_ID);
        verify(userDaoMock, never()).deleteUser(NULL_ID);
    }

    @Test(expected = InternalServerException.class)
    public void deleteUserUnSuccess_3_MockTest() {
        when(userDaoMock.deleteUser(CORRECT_ID)).thenReturn(0);
        userServiceImpl.deleteUser(CORRECT_ID);
    }
}
