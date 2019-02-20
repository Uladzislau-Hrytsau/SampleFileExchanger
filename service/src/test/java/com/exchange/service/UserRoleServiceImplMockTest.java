package com.exchange.service;

import com.exchange.dao.UserRoleDao;
import com.exchange.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * The type User role service impl mock test.
 */
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

    /**
     * Gets roles by user name success 1 mock test.
     */
    @Test
    public void getRolesByUserNameSuccess_1_MockTest() {
        userRoleService.getRolesByUserName(CORRECT_LOGIN);
        verify(userRoleDaoMock, times(ONE)).getRolesByUserName(any());
    }

    /**
     * Gets roles by user name un success 1 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getRolesByUserNameUnSuccess_1_MockTest() {
        userRoleService.getRolesByUserName(EMPTY);
        verify(userRoleDaoMock, never()).getRolesByUserName(any());
    }

    /**
     * Gets roles by user name un success 2 mock test.
     */
    @Test(expected = ValidationException.class)
    public void getRolesByUserNameUnSuccess_2_MockTest() {
        userRoleService.getRolesByUserName(NULL);
        verify(userRoleDaoMock, never()).getRolesByUserName(any());
    }

}
