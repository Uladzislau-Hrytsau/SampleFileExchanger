package com.exchange.service;

import com.exchange.dao.Pagination;
import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.exception.FileNotDeletedException;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.implementation.UserServiceImpl;
import com.exchange.service.validation.CommonValidator;
import com.exchange.service.validation.UserValidator;
import com.exchange.wrapper.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Long CORRECT_USER_ID = 1L;
    private static final Long INCORRECT_USER_ID = -1L;
    private static final Integer CORRECT_PAGE = 2;
    private static final Integer CORRECT_SIZE = 10;
    private static final String CORRECT_LOGIN = "correctLogin";
    private static final String CORRECT_PASSWORD = "correctPassword";
    private static final String CORRECT_INFORMATION = "correctInformation";
    private static final Boolean CORRECT_GENDER = Boolean.TRUE;
    private static final LocalDate CORRECT_BIRTH_DATE = LocalDate.of(1000, 10, 10);
    private static final String CORRECT_ENCODE_PASSWORD = "810711ba-8589-4e54-9453-9df3e398a5c0";
    private static final User CORRECT_USER = new User(CORRECT_LOGIN, CORRECT_PASSWORD);
    private static final UserUpdatingDto CORRECT_USER_UPDATING_DTO = new UserUpdatingDto(
            CORRECT_USER_ID,
            CORRECT_PASSWORD,
            CORRECT_GENDER,
            CORRECT_INFORMATION,
            CORRECT_BIRTH_DATE);
    private static final List<User> CORRECT_USERS = Arrays.asList(CORRECT_USER, CORRECT_USER);

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;
    @Mock
    private UserDao userDaoMock;
    @Mock
    private UserValidator userValidatorMock;
    @Mock
    private RoleService roleServiceMock;
    @Mock
    private CommonService commonServiceMock;
    @Mock
    private CommonValidator commonValidatorMock;
    @Mock
    private FileService fileServiceMock;
    @Mock
    private FileWriterService fileWriterServiceMock;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUsersAndCountByPageAndSize_correctPageAndSize_correctResponseReturned() {
        doNothing().when(commonValidatorMock).validatePageAndSize(any(), any());
        when(userDaoMock.getUsersByLimitAndOffset(any(), any())).thenReturn(CORRECT_USERS);
        when(userDaoMock.getUserCount()).thenReturn(Long.valueOf(CORRECT_USERS.size()));
        Response actualUsersAndCountByPageAndSize =
                userService.getUsersAndCountByPageAndSize(CORRECT_PAGE, CORRECT_SIZE);
        Response expectedUsersAndCountByPageAndSize =
                new Response(CORRECT_USERS, new Pagination(Long.valueOf(CORRECT_USERS.size())));
        assertEquals(expectedUsersAndCountByPageAndSize, actualUsersAndCountByPageAndSize);
        verify(commonValidatorMock, times(TIMES_ONE)).validatePageAndSize(any(), any());
        verify(userDaoMock, times(TIMES_ONE)).getUsersByLimitAndOffset(any(), any());
        verify(userDaoMock, times(TIMES_ONE)).getUserCount();
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = ValidationException.class)
    public void getUsersAndCountByPageAndSize_incorrectPageAndSize_validationException() {
        doThrow(ValidationException.class).when(commonValidatorMock).validatePageAndSize(any(), any());
        userService.getUsersAndCountByPageAndSize(CORRECT_PAGE, CORRECT_SIZE);
        verify(commonValidatorMock, times(TIMES_ONE)).validatePageAndSize(any(), any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test
    public void addUser_correctUser() {
        doNothing().when(userValidatorMock).validateExistingLogin(CORRECT_LOGIN, userDaoMock);
        doNothing().when(userValidatorMock).validatePassword(any());
        when(bCryptPasswordEncoderMock.encode(any())).thenReturn(CORRECT_ENCODE_PASSWORD);
        when(userDaoMock.addUser(any())).thenReturn(CORRECT_USER_ID);
        when(commonValidatorMock.isValidIdentifier(any())).thenReturn(Boolean.TRUE);
        doNothing().when(roleServiceMock).addUserRole(any(), any());
        userService.addUser(CORRECT_USER);
        verify(userValidatorMock, times(TIMES_ONE)).validateExistingLogin(any(), any());
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(bCryptPasswordEncoderMock, times(TIMES_ONE)).encode(any());
        verify(userDaoMock, times(TIMES_ONE)).addUser(any());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(any());
        verify(roleServiceMock, times(TIMES_ONE)).addUserRole(any(), any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = ValidationException.class)
    public void addUser_incorrectUserLogin_validationException() {
        doThrow(ValidationException.class).when(userValidatorMock).validateExistingLogin(any(), any());
        when(userDaoMock.addUser(any())).thenReturn(CORRECT_USER_ID);
        userService.addUser(CORRECT_USER);
        verify(userValidatorMock, times(TIMES_ONE)).validateExistingLogin(any(), any());
        verify(userValidatorMock, never()).validatePassword(any());
        verify(bCryptPasswordEncoderMock, never()).encode(any());
        verify(userDaoMock, never()).addUser(any());
        verify(commonValidatorMock, never()).isValidIdentifier(any());
        verify(roleServiceMock, never()).addUserRole(any(), any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = ValidationException.class)
    public void addUser_incorrectUserPassword_validationException() {
        doNothing().when(userValidatorMock).validateExistingLogin(any(), any());
        doThrow(ValidationException.class).when(userValidatorMock).validatePassword(any());
        when(userDaoMock.addUser(any())).thenReturn(CORRECT_USER_ID);
        userService.addUser(CORRECT_USER);
        verify(userValidatorMock, times(TIMES_ONE)).validateExistingLogin(any(), any());
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(userValidatorMock, never()).validatePassword(any());
        verify(bCryptPasswordEncoderMock, never()).encode(any());
        verify(userDaoMock, never()).addUser(any());
        verify(commonValidatorMock, never()).isValidIdentifier(any());
        verify(roleServiceMock, never()).addUserRole(any(), any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = InternalServerException.class)
    public void addUser_incorrectUserId_internalServerException() {
        doNothing().when(userValidatorMock).validateExistingLogin(any(), any());
        doNothing().when(userValidatorMock).validatePassword(any());
        when(bCryptPasswordEncoderMock.encode(any())).thenReturn(CORRECT_ENCODE_PASSWORD);
        when(userDaoMock.addUser(any())).thenReturn(INCORRECT_USER_ID);
        when(commonValidatorMock.isValidIdentifier(any())).thenReturn(Boolean.FALSE);
        userService.addUser(CORRECT_USER);
        verify(userValidatorMock, times(TIMES_ONE)).validateExistingLogin(any(), any());
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(bCryptPasswordEncoderMock, times(TIMES_ONE)).encode(any());
        verify(userDaoMock, times(TIMES_ONE)).addUser(any());
        verify(commonValidatorMock, times(TIMES_ONE)).isValidIdentifier(any());
        verify(roleServiceMock, never()).addUserRole(any(), any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test
    public void updateUser_correctUserUpdatingDto() {
        doNothing().when(userValidatorMock).validatePassword(any());
        doNothing().when(userValidatorMock).validateInformation(any());
        when(commonValidatorMock.validateDate(any())).thenReturn(CORRECT_BIRTH_DATE);
        when(bCryptPasswordEncoderMock.encode(any())).thenReturn(CORRECT_ENCODE_PASSWORD);
        when(userDaoMock.updateUser(any())).thenReturn(Boolean.TRUE);
        userService.updateUser(CORRECT_USER_UPDATING_DTO);
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(userValidatorMock, times(TIMES_ONE)).validateInformation(any());
        verify(commonValidatorMock, times(TIMES_ONE)).validateDate(any());
        verify(bCryptPasswordEncoderMock, times(TIMES_ONE)).encode(any());
        verify(userDaoMock, times(TIMES_ONE)).updateUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = ValidationException.class)
    public void updateUser_incorrectUserUpdatingDtoPassword_validationException() {
        doThrow(ValidationException.class).when(userValidatorMock).validatePassword(any());
        userService.updateUser(CORRECT_USER_UPDATING_DTO);
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(userValidatorMock, never()).validateInformation(any());
        verify(commonValidatorMock, never()).validateDate(any());
        verify(bCryptPasswordEncoderMock, never()).encode(any());
        verify(userDaoMock, never()).updateUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = ValidationException.class)
    public void updateUser_incorrectUserUpdatingDtoInformation_validationException() {
        doNothing().when(userValidatorMock).validatePassword(any());
        doThrow(ValidationException.class).when(userValidatorMock).validateInformation(any());
        userService.updateUser(CORRECT_USER_UPDATING_DTO);
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(userValidatorMock, times(TIMES_ONE)).validateInformation(any());
        verify(commonValidatorMock, never()).validateDate(any());
        verify(bCryptPasswordEncoderMock, never()).encode(any());
        verify(userDaoMock, never()).updateUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = InternalServerException.class)
    public void updateUser_incorrectUserUpdatingDtoInformation_internalServerException() {
        doNothing().when(userValidatorMock).validatePassword(any());
        doNothing().when(userValidatorMock).validateInformation(any());
        when(commonValidatorMock.validateDate(any())).thenReturn(CORRECT_BIRTH_DATE);
        when(bCryptPasswordEncoderMock.encode(any())).thenReturn(CORRECT_ENCODE_PASSWORD);
        when(userDaoMock.updateUser(any())).thenReturn(Boolean.FALSE);
        userService.updateUser(CORRECT_USER_UPDATING_DTO);
        verify(userValidatorMock, times(TIMES_ONE)).validatePassword(any());
        verify(userValidatorMock, times(TIMES_ONE)).validateInformation(any());
        verify(commonValidatorMock, never()).validateDate(any());
        verify(bCryptPasswordEncoderMock, never()).encode(any());
        verify(userDaoMock, never()).updateUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test
    public void deleteUser_correctUserId() {
        doNothing().when(userValidatorMock).validateUserId(any());
        doNothing().when(fileWriterServiceMock).deleteFilesByNames(any());
        when(userDaoMock.deleteUser(any())).thenReturn(Boolean.TRUE);
        userService.deleteUser(CORRECT_USER_ID);
        verify(userValidatorMock, times(TIMES_ONE)).validateUserId(any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFilesByNames(any());
        verify(userDaoMock, times(TIMES_ONE)).deleteUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileWriterServiceMock);
    }

    @Test(expected = ValidationException.class)
    public void deleteUser_incorrectUserId_validationException() {
        doThrow(ValidationException.class).when(userValidatorMock).validateUserId(any());
        userService.deleteUser(CORRECT_USER_ID);
        verify(userValidatorMock, times(TIMES_ONE)).validateUserId(any());
        verify(fileWriterServiceMock, never()).deleteFilesByNames(any());
        verify(userDaoMock, never()).deleteUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = FileNotDeletedException.class)
    public void deleteUser_incorrectFileNames_fileNotDeletedException() {
        doNothing().when(userValidatorMock).validateUserId(any());
        doThrow(FileNotDeletedException.class).when(fileWriterServiceMock).deleteFilesByNames(any());
        userService.deleteUser(CORRECT_USER_ID);
        verify(userValidatorMock, times(TIMES_ONE)).validateUserId(any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFilesByNames(any());
        verify(userDaoMock, never()).deleteUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test(expected = InternalServerException.class)
    public void deleteUser_incorrectUserId_internalServerException() {
        doNothing().when(userValidatorMock).validateUserId(any());
        doNothing().when(fileWriterServiceMock).deleteFilesByNames(any());
        when(userDaoMock.deleteUser(any())).thenReturn(Boolean.FALSE);
        userService.deleteUser(CORRECT_USER_ID);
        verify(userValidatorMock, times(TIMES_ONE)).validateUserId(any());
        verify(fileWriterServiceMock, times(TIMES_ONE)).deleteFilesByNames(any());
        verify(userDaoMock, times(TIMES_ONE)).deleteUser(any());
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

    @Test
    public void getUserCount_correctCountReturned() {
        when(userDaoMock.getUserCount()).thenReturn(Long.valueOf(CORRECT_SIZE));
        userService.getUserCount();
        verify(userDaoMock, times(TIMES_ONE)).getUserCount();
        verifyNoMoreInteractions(
                bCryptPasswordEncoderMock,
                userDaoMock,
                userValidatorMock,
                roleServiceMock,
                commonServiceMock,
                commonValidatorMock,
                fileServiceMock,
                fileWriterServiceMock);
    }

}
