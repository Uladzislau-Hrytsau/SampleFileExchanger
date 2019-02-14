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

/**
 * The type User service impl mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplMockTest {

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
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    /**
     * Gets user by user id success 1 mock test.
     */
    @Test
    public void getUserByUserIdSuccess_1_MockTest() {
        when(userDaoMock.checkUserByUserId(anyLong())).thenReturn(true);
        userServiceImpl.getUserByUserId(CORRECT_ID);
        verify(userDaoMock, times(1)).getUserByUserId(anyLong());
    }

    /**
     * Gets user by user id un success 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getUserByUserIdUnSuccess_1_MockTest() {
        userServiceImpl.getUserByUserId(NULL_ID);
        verify(userDaoMock, never()).checkUserByUserId(anyLong());
        verify(userDaoMock, never()).getUserByUserId(anyLong());
    }

    /**
     * Gets user by user id un success 2 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getUserByUserIdUnSuccess_2_MockTest() {
        userServiceImpl.getUserByUserId(INCORRECT_ID);
        verify(userDaoMock, never()).checkUserByUserId(anyLong());
        verify(userDaoMock, never()).getUserByUserId(anyLong());
    }

    /**
     * Gets user by user id un success 3 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getUserByUserIdUnSuccess_3_MockTest() {
        when(userDaoMock.checkUserByUserId(anyLong())).thenReturn(false);
        userServiceImpl.getUserByUserId(CORRECT_ID);
        verify(userDaoMock, never()).getUserByUserId(anyLong());
    }


    /**
     * Gets user by login success 1 mock test.
     */
    @Test
    public void getUserByLoginSuccess_1_MockTest() {
        when(userDaoMock.checkUserByLogin(anyString())).thenReturn(true);
        userServiceImpl.getUserByLogin(CORRECT_LOGIN);
        verify(userDaoMock, times(1)).getUserByLogin(anyString());
    }

    /**
     * Gets user by login un success 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getUserByLoginUnSuccess_1_MockTest() {
        userServiceImpl.getUserByLogin(NULL_LOGIN);
        verify(userDaoMock, never()).checkUserByLogin(anyString());
        verify(userDaoMock, never()).getUserByLogin(anyString());
    }

    /**
     * Gets user by login un success 2 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getUserByLoginUnSuccess_2_MockTest() {
        userServiceImpl.getUserByLogin(EMPTY_LOGIN);
        verify(userDaoMock, never()).checkUserByLogin(anyString());
        verify(userDaoMock, never()).getUserByLogin(anyString());
    }

    /**
     * Gets user by login un success 3 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getUserByLoginUnSuccess_3_MockTest() {
        when(userDaoMock.checkUserByLogin(anyString())).thenReturn(false);
        userServiceImpl.getUserByLogin(CORRECT_LOGIN);
        verify(userDaoMock, never()).getUserByLogin(anyString());
    }


    /**
     * Add user success 1 mock test.
     */
    @Test
    public void addUserSuccess_1_MockTest() {
        doNothing().when(userValidatorMock).validateLoginAndPassword(userMock);
        doNothing().when(userValidatorMock).validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, times(1)).addUser(userMock);
    }

    /**
     * Add user un success 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void addUserUnSuccess_1_MockTest() {
        doThrow(ValidationException.class).when(userValidatorMock).validateLoginAndPassword(userMock);
        doThrow(ValidationException.class).when(userValidatorMock).validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        userServiceImpl.addUser(userMock);
        verify(userDaoMock, never()).addUser(any(User.class));
    }


    /**
     * Update user success 1 mock test.
     */
    @Test
    public void updateUserSuccess_1_MockTest() {
        doNothing().when(userValidatorMock).validateLoginAndPassword(userMock);
        doNothing().when(userValidatorMock).validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        when(userDaoMock.updateUser(userMock)).thenReturn(1);
        userServiceImpl.updateUser(userMock);
    }

    /**
     * Update user un success 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void updateUserUnSuccess_1_MockTest() {
        doThrow(ValidationException.class).when(userValidatorMock).validateLoginAndPassword(userMock);
        doThrow(ValidationException.class).when(userValidatorMock).validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        userServiceImpl.updateUser(userMock);
        verify(userDaoMock, never()).updateUser(any(User.class));
    }

    /**
     * Update user un success 2 mock test.
     */
    @Test(expected = InternalServerException.class)
    public void updateUserUnSuccess_2_MockTest() {
        doNothing().when(userValidatorMock).validateLoginAndPassword(any());
        doNothing().when(userValidatorMock).validateExistingLogin(any(), any());
        when(userDaoMock.updateUser(any(User.class))).thenReturn(0);
        userServiceImpl.updateUser(userMock);
    }


    /**
     * Delete user success 1 mock test.
     */
    @Test
    public void deleteUserSuccess_1_MockTest() {
        when(userDaoMock.deleteUser(CORRECT_ID)).thenReturn(1);
        userServiceImpl.deleteUser(CORRECT_ID);
    }

    /**
     * Delete user un success 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void deleteUserUnSuccess_1_MockTest() {
        userServiceImpl.deleteUser(INCORRECT_ID);
        verify(userDaoMock, never()).deleteUser(INCORRECT_ID);
    }

    /**
     * Delete user un success 2 mock test.
     */
    @Test(expected = ValidationException.class)
    public void deleteUserUnSuccess_2_MockTest() {
        userServiceImpl.deleteUser(NULL_ID);
        verify(userDaoMock, never()).deleteUser(NULL_ID);
    }

    /**
     * Delete user un success 3 mock test.
     */
    @Test(expected = InternalServerException.class)
    public void deleteUserUnSuccess_3_MockTest() {
        when(userDaoMock.deleteUser(CORRECT_ID)).thenReturn(0);
        userServiceImpl.deleteUser(CORRECT_ID);
    }
}
