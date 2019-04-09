package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dao.Pagination;
import com.exchange.dao.User;
import com.exchange.dao.exception.FileNotDeletedException;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.UserService;
import com.exchange.wrapper.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User rest controller mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerMockTest {

    /**
     * The constant TIMES_ONE.
     */
    public static final int TIMES_ONE = 1;
    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final String USERS_URI = "/users";
    private static final Integer CORRECT_PAGE = 1;
    private static final Integer CORRECT_SIZE = 2;
    private static final Long CORRECT_COUNT = 2L;
    private static final Long CORRECT_ID = 1L;
    private static final User CORRECT_USER = new User("correctName", "correctPassword");
    private static final List<User> CORRECT_USERS = Arrays.asList(
            CORRECT_USER,
            CORRECT_USER);
    private static final UserUpdatingDto CORRECT_USER_UPDATING_DTO = new UserUpdatingDto(
            1L, "password", true, "information", LocalDate.of(1000, 10, 10));
    private static final Pagination CORRECT_PAGINATION = new Pagination(CORRECT_COUNT);
    private static final Response CORRECT_RESPONSE = new Response(
            CORRECT_USERS,
            CORRECT_PAGINATION);
    private static final String ID_PARAM = "id";
    @Mock
    private UserService userServiceMock;
    @InjectMocks
    private UserRestController userRestController;
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    /**
     * Gets users by page and size correct page and size correct response returned.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUsersByPageAndSize_correctPageAndSize_correctResponseReturned() throws Exception {
        given(userServiceMock.getUsersAndCountByPageAndSize(any(Integer.class), any(Integer.class)))
                .willReturn(CORRECT_RESPONSE);
        mockMvc.perform(get(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(PAGE_PARAM, String.valueOf(CORRECT_PAGE))
                .param(SIZE_PARAM, String.valueOf(CORRECT_SIZE)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(CORRECT_RESPONSE)));
        verify(userServiceMock, times(TIMES_ONE)).getUsersAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Gets users by page and size non page and size validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUsersByPageAndSize_nonPageAndSize_validationException() throws Exception {
        mockMvc.perform(get(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, never()).getUsersAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Add user correct user.
     *
     * @throws Exception the exception
     */
    @Test
    public void addUser_correctUser() throws Exception {
        doNothing().when(userServiceMock).addUser(any(User.class));
        mockMvc.perform(post(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_USER)))
                .andExpect(status().isCreated());
        verify(userServiceMock, times(TIMES_ONE)).addUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Add user incorrect user validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void addUser_incorrectUser_validationException() throws Exception {
        doThrow(ValidationException.class).when(userServiceMock).addUser(any(User.class));
        mockMvc.perform(post(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_USER)))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, times(TIMES_ONE)).addUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Add user incorrect user internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void addUser_incorrectUser_internalServerException() throws Exception {
        doThrow(InternalServerException.class).when(userServiceMock).addUser(any(User.class));
        mockMvc.perform(post(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_USER)))
                .andExpect(status().isInternalServerError());
        verify(userServiceMock, times(TIMES_ONE)).addUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Add user not user internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void addUser_notUser_internalServerException() throws Exception {
        mockMvc.perform(post(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, never()).addUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Update user correct user updating dto.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUser_correctUserUpdatingDto() throws Exception {
        doNothing().when(userServiceMock).updateUser(any(UserUpdatingDto.class));
        mockMvc.perform(put(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_USER_UPDATING_DTO)))
                .andExpect(status().isOk());
        verify(userServiceMock, times(TIMES_ONE)).updateUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Update user incorrect user updating dto validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUser_incorrectUserUpdatingDto_validationException() throws Exception {
        doThrow(ValidationException.class).when(userServiceMock).updateUser(any(UserUpdatingDto.class));
        mockMvc.perform(put(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_USER_UPDATING_DTO)))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, times(TIMES_ONE)).updateUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Update user incorrect user updating dto internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUser_incorrectUserUpdatingDto_internalServerException() throws Exception {
        doThrow(InternalServerException.class).when(userServiceMock).updateUser(any(UserUpdatingDto.class));
        mockMvc.perform(put(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(CORRECT_USER_UPDATING_DTO)))
                .andExpect(status().isInternalServerError());
        verify(userServiceMock, times(TIMES_ONE)).updateUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Update user non user updating dto internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUser_nonUserUpdatingDto_internalServerException() throws Exception {
        mockMvc.perform(put(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, never()).updateUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Delete user correct user id.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUser_correctUserId() throws Exception {
        doNothing().when(userServiceMock).deleteUser(any(Long.class));
        mockMvc.perform(delete(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isOk());
        verify(userServiceMock, times(TIMES_ONE)).deleteUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Delete user incorrect user id validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUser_incorrectUserId_validationException() throws Exception {
        doThrow(ValidationException.class).when(userServiceMock).deleteUser(any(Long.class));
        mockMvc.perform(delete(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, times(TIMES_ONE)).deleteUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Delete user incorrect user id file not deleted exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUser_incorrectUserId_fileNotDeletedException() throws Exception {
        doThrow(FileNotDeletedException.class).when(userServiceMock).deleteUser(any(Long.class));
        mockMvc.perform(delete(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isInternalServerError());
        verify(userServiceMock, times(TIMES_ONE)).deleteUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Delete user incorrect user id internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUser_incorrectUserId_internalServerException() throws Exception {
        doThrow(InternalServerException.class).when(userServiceMock).deleteUser(any(Long.class));
        mockMvc.perform(delete(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(ID_PARAM, String.valueOf(CORRECT_ID)))
                .andExpect(status().isInternalServerError());
        verify(userServiceMock, times(TIMES_ONE)).deleteUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

    /**
     * Delete user non user id internal server exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUser_nonUserId_internalServerException() throws Exception {
        mockMvc.perform(delete(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(userServiceMock, never()).deleteUser(any());
        verifyNoMoreInteractions(userServiceMock);
    }

}
