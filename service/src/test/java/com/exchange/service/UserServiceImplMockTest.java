package com.exchange.service;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplMockTest {

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;

    private static final Long CORRECT_ID = 1L;
    private static final Long NULL_ID = null;
    private static final Long INCORRECT_ID = -1L;
    private static final String CORRECT_LOGIN = "login";
    private static final String NULL_LOGIN = null;
    private static final String EMPTY_LOGIN = "";

    @Mock
    private UserDao userDaoMock;
    @Mock
    private UserValidator userValidatorMock;
    @Mock
    private User userMock;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void getAllUsersSuccess_1_MockTest() {
        userServiceImpl.getAllUsers();
        verify(userDaoMock, times(ONE)).getAllUsers();
    }

    @Test
    public void getUserByUserIdSuccess_1_MockTest() {
        when(userDaoMock.checkUserByUserId(anyLong())).thenReturn(true);
        userServiceImpl.getUserByUserId(CORRECT_ID);
        verify(userDaoMock, times(ONE)).getUserByUserId(anyLong());
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
        verify(userDaoMock, times(ONE)).getUserByLogin(anyString());
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
    public void getUserPasswordByUserNameSuccess_1_MockTest() {
        userServiceImpl.getUserPasswordByUserName(CORRECT_LOGIN);
        verify(userDaoMock, times(ONE)).getUserPasswordByUserName(any());
    }

    @Test(expected = ValidationException.class)
    public void getUserPasswordByUserNameUnSuccess_1_MockTest() {
        userServiceImpl.getUserPasswordByUserName(EMPTY_LOGIN);
        verify(userDaoMock, never()).getUserPasswordByUserName(any());
    }

    @Test(expected = ValidationException.class)
    public void getUserPasswordByUserNameUnSuccess_2_MockTest() {
        userServiceImpl.getUserPasswordByUserName(NULL_LOGIN);
        verify(userDaoMock, never()).getUserPasswordByUserName(any());
    }

    @Test
    public void addUserSuccess_1_MockTest() {
        doNothing().when(userValidatorMock).validateExistingLogin(any(), any());
        doNothing().when(userValidatorMock).validatePassword(any());
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, times(ONE)).addUser(any());
    }

    @Test(expected = ValidationException.class)
    public void addUserUnSuccess_1_MockTest() {
        doThrow(ValidationException.class).when(userValidatorMock).validateExistingLogin(any(), any());
        doNothing().when(userValidatorMock).validatePassword(any());
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, never()).addUser(any());
    }

    @Test(expected = ValidationException.class)
    public void addUserUnSuccess_2_MockTest() {
        doNothing().when(userValidatorMock).validateExistingLogin(any(), any());
        doThrow(ValidationException.class).when(userValidatorMock).validatePassword(any());
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, never()).addUser(any());
    }

    @Test
    public void updateUserSuccess_1_MockTest() {
        doNothing().when(userValidatorMock).validatePassword(any());
        when(userDaoMock.updateUser(any())).thenReturn(ONE);
        userServiceImpl.updateUser(userMock);
        verify(userDaoMock, times(ONE)).updateUser(any());
    }

    @Test(expected = ValidationException.class)
    public void updateUserUnSuccess_1_MockTest() {
        doThrow(ValidationException.class).when(userValidatorMock).validatePassword(any());
        when(userDaoMock.updateUser(any())).thenReturn(ONE);
        userServiceImpl.updateUser(userMock);
        verify(userDaoMock, never()).updateUser(any());
    }

    @Test(expected = InternalServerException.class)
    public void updateUserUnSuccess_2_MockTest() {
        doNothing().when(userValidatorMock).validatePassword(any());
        when(userDaoMock.updateUser(any())).thenReturn(ZERO);
        userServiceImpl.updateUser(userMock);
        verify(userDaoMock, times(ONE)).updateUser(any());
    }

    @Test
    public void deleteUserSuccess_1_MockTest() {
        when(userDaoMock.deleteUser(CORRECT_ID)).thenReturn(ONE);
        userServiceImpl.deleteUser(CORRECT_ID);
        verify(userDaoMock, times(ONE)).deleteUser(any());
    }

    @Test(expected = ValidationException.class)
    public void deleteUserUnSuccess_1_MockTest() {
        userServiceImpl.deleteUser(INCORRECT_ID);
        verify(userDaoMock, never()).deleteUser(any());
    }

    @Test(expected = ValidationException.class)
    public void deleteUserUnSuccess_2_MockTest() {
        userServiceImpl.deleteUser(NULL_ID);
        verify(userDaoMock, never()).deleteUser(any());
    }

    @Test(expected = InternalServerException.class)
    public void deleteUserUnSuccess_3_MockTest() {
        when(userDaoMock.deleteUser(CORRECT_ID)).thenReturn(ZERO);
        userServiceImpl.deleteUser(CORRECT_ID);
        verify(userDaoMock, never()).deleteUser(any());
    }
}
