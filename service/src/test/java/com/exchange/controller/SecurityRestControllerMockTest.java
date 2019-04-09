package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Security rest controller mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class SecurityRestControllerMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final String OAUTH_ROLE_URI = "/oauth/role";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final Set<String> CORRECT_ROLES = new HashSet<>(Arrays.asList(
            ROLE_USER,
            ROLE_ADMIN));

    @Mock
    private RoleService roleServiceMock;
    @Mock
    private Authentication authenticationMock;
    @InjectMocks
    private SecurityRestController securityRestController;
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(securityRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    /**
     * Add folder correct folder structure dto and authentication correct roles returned.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFolder_correctFolderStructureDtoAndAuthentication_correctRolesReturned() throws Exception {
        given(roleServiceMock.getRolesByAuthentication(any(Authentication.class)))
                .willReturn(CORRECT_ROLES);
        mockMvc.perform(get(OAUTH_ROLE_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .principal(authenticationMock))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(CORRECT_ROLES)));
        verify(roleServiceMock, times(TIMES_ONE)).getRolesByAuthentication(authenticationMock);
        verifyNoMoreInteractions(roleServiceMock);
    }
}
