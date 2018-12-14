package com.exchange.controller;

import com.exchange.dao.User;
import com.exchange.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-rest-mock.xml"})
public class UserRestControllerMockTest {

    @Resource
    private UserRestController userController;

    @Mock
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(userController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
//        verify(userService);
//        reset(userService);
    }

    @Test
    public void getAllUsersTest() throws Exception {
//        expect(userService.getAllUsers()).andReturn(Collections.<User>singletonList(new User("l", "p")));
//        replay(userService);
        mockMvc.perform(
                get("/user")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByUserIdTest() throws Exception {
        User user = new User(
                1L, "userLogin1", "userPassword1", true,
                LocalDate.of(1000, 10, 10), "userInformation1"
        );
//        when(userService.getUserByUserId(user.getUserId())).thenReturn(user);
//        expect(userService.getUserByUserId(1L)).andReturn(user);
//        replay(userService);

//        mockMvc.perform(
//                get("/user/{id}", 1L)
//                        .accept(MediaType.APPLICATION_JSON)
//        )
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    public void addUserTest() throws Exception {
//        String user = new ObjectMapper().writeValueAsString(new User("login2", "password2"));

        mockMvc.perform(
                post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(user)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("3"));
    }

    @Test
    public void getUsersTest() throws Exception {

        mockMvc.perform(
                get("/users")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void updateUserTest() throws Exception {

        mockMvc.perform(
                put("/user/2/login5/password5/desc5")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

}
