package com.exchange.controller;

import com.exchange.controller.converter.JsonConverter;
import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dao.User;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.UserService;
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
import java.util.Collections;

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

    private static final Long ID = 1L;
    private static final String LOGIN = "userLogin";
    private static final User user = new User(
            1L, "userLogin", "userPassword",
            true, LocalDate.of(2019, 1, 11), "userInformation"
    );

    private static final String USER_JSON = JsonConverter.asJsonString(user);

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
                .build();
    }

    /**
     * Gets all users success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getAllUsersSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getAllUsers()).
                willReturn(Collections.singletonList(user));
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(content().json("[" + USER_JSON + "]"));
    }

    /**
     * Gets user by user id success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUserByUserIdSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByUserId(ID)).willReturn(user);
        mockMvc.perform(get("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(USER_JSON));
//                .andExpect(content().json(USER_JSON));
    }

    /**
     * Gets user by user id un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUserByUserIdUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByUserId(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Gets user by login success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUserByLoginSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByLogin(anyString())).willReturn(user);
        mockMvc.perform(get("/user/login/{login}", LOGIN)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(USER_JSON));
    }

    /**
     * Gets user by login un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getUserByLoginUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByLogin(anyString())).willThrow(ValidationException.class);
        mockMvc.perform(get("/user/login/{login}", LOGIN)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Add user success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void addUserSuccess_1_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(2L);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(USER_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Add user un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void addUserUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willThrow(ValidationException.class);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(USER_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Update user success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUserSuccess_1_MockTest() throws Exception {
        doNothing().when(userServiceMock).updateUser(user);
        mockMvc.perform(put("/user")
                .content(USER_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Update user un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUserUnSuccess_1_MockTest() throws Exception {
        doThrow(ValidationException.class).when(userServiceMock).updateUser(any(User.class));
        mockMvc.perform(put("/user")
                .content(USER_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Update user un success 2 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUserUnSuccess_2_MockTest() throws Exception {
        doThrow(InternalServerException.class).when(userServiceMock).updateUser(any(User.class));
        mockMvc.perform(put("/user")
                .content(USER_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    /**
     * Delete user success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUserSuccess_1_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(1L);
        mockMvc.perform(delete("/user/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Delete user un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUserUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(1L);
        doThrow(ValidationException.class).when(userServiceMock).deleteUser(1L);
        mockMvc.perform(delete("/user/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Delete user un success 2 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteUserUnSuccess_2_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(1L);
        doThrow(InternalServerException.class).when(userServiceMock).deleteUser(1L);
        mockMvc.perform(delete("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }


}
