package com.exchange.service;

import com.exchange.dao.UserRoleDao;
import com.exchange.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceImplMockTest {

    private static final String CORRECT_LOGIN = "login";
    private static final String EMPTY = "";
    private static final String NULL = null;

    private static final int ONE = 1;

    @Mock
    private UserRoleDao userRoleDaoMock;
    @InjectMocks
    private UserRoleServiceImpl userRoleService;
    private MockMvc mockMvc;

    @Test
    public void getRolesByUserNameSuccess_1_MockTest() {
        userRoleService.getRolesByUserName(CORRECT_LOGIN);
        verify(userRoleDaoMock, times(ONE)).getRolesByUserName(any());
    }

    @Test(expected = ValidationException.class)
    public void getRolesByUserNameUnSuccess_1_MockTest() {
        userRoleService.getRolesByUserName(EMPTY);
        verify(userRoleDaoMock, never()).getRolesByUserName(any());
    }

    @Test(expected = ValidationException.class)
    public void getRolesByUserNameUnSuccess_2_MockTest() {
        userRoleService.getRolesByUserName(NULL);
        verify(userRoleDaoMock, never()).getRolesByUserName(any());
    }

}
