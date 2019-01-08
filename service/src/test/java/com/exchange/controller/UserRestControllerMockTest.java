package com.exchange.controller;

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

import static com.exchange.controller.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerMockTest {

    private static final Long ID = 1L;
    private static final String LOGIN = "userLogin";
    private static final User user = new User(
            1L, "userLogin", "userPassword",
            true, LocalDate.of(2000, 2, 2), "userInformation"
    );
    private static final User usr = new User(2L, "login", "password");
    private String userJson;
    @Mock
    private UserService userServiceMock;
    @InjectMocks
    private UserRestController userRestController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setControllerAdvice(new RestErrorHandler())
                .build();
    }

    @Test
    public void getAllUsersSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getAllUsers()).
                willReturn(Collections.singletonList(user));
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(content().json("[" + asJsonString(user) + "]"));
    }

    @Test
    public void getUserByUserIdSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByUserId(ID)).willReturn(user);
        mockMvc.perform(get("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(user)));
    }

    @Test
    public void getUserByUserIdUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByUserId(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserByLoginSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByLogin(anyString())).willReturn(user);
        mockMvc.perform(get("/user/login/{login}", LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(user)));
    }

    @Test
    public void getUserByLoginUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.getUserByLogin(anyString())).willThrow(ValidationException.class);
        mockMvc.perform(get("/user/login/{login}", LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserSuccess_1_MockTest() throws Exception {
        userJson = asJsonString(usr);
        given(userServiceMock.addUser(usr)).willReturn(2L);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addUserUnSuccess_1_MockTest() throws Exception {
        userJson = asJsonString(usr);
        given(userServiceMock.addUser(usr)).willThrow(ValidationException.class);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserSuccess_1_MockTest() throws Exception {
        doNothing().when(userServiceMock).updateUser(usr);
        mockMvc.perform(put("/user")
                .content(asJsonString(usr))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserUnSuccess_1_MockTest() throws Exception {
        doThrow(ValidationException.class).when(userServiceMock).updateUser(any(User.class));
        mockMvc.perform(put("/user")
                .content(asJsonString(usr))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserUnSuccess_2_MockTest() throws Exception {
        doThrow(InternalServerException.class).when(userServiceMock).updateUser(any(User.class));
        mockMvc.perform(put("/user")
                .content(asJsonString(usr))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteUserSuccess_1_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(1L);
        mockMvc.perform(delete("/user/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserUnSuccess_1_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(1L);
        doThrow(ValidationException.class).when(userServiceMock).deleteUser(1L);
        mockMvc.perform(delete("/user/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserUnSuccess_2_MockTest() throws Exception {
        given(userServiceMock.addUser(user)).willReturn(1L);
        doThrow(InternalServerException.class).when(userServiceMock).deleteUser(1L);
        mockMvc.perform(delete("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }


}