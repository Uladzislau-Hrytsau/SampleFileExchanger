package com.exchange.service;

import com.exchange.configuration.security.userdetails.UserDetails;
import com.exchange.dao.RoleDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.implementation.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The type Role service impl mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final String CORRECT_USER_ROLE = "USER_ROLE";
    private static final String CORRECT_ADMIN_ROLE = "ADMIN_ROLE";
    private static final Set<String> CORRECT_ROLES = new HashSet<>(Arrays.asList(CORRECT_ADMIN_ROLE, CORRECT_USER_ROLE));
    private static final Collection<GrantedAuthority> CORRECT_GRANTED_AUTHORITY = new HashSet<>(Arrays.asList(
            new SimpleGrantedAuthority(CORRECT_ADMIN_ROLE),
            new SimpleGrantedAuthority(CORRECT_USER_ROLE)));

    @Mock
    private RoleDao roleDaoMock;
    @Mock
    private CommonService commonServiceMock;
    @Mock
    private Authentication authenticationMock;
    @Mock
    private UserDetails userDetailsMock;
    @InjectMocks
    private RoleServiceImpl roleService;

    /**
     * Gets roles by authentication correct authentication correct roles returned.
     */
    @Test
    public void getRolesByAuthentication_correctAuthentication_correctRolesReturned() {
        when(commonServiceMock.getAuthoritiesByAuthentication(authenticationMock)).thenReturn(CORRECT_GRANTED_AUTHORITY);
        Set<String> actualRoles = roleService.getRolesByAuthentication(authenticationMock);
        assertEquals(CORRECT_ROLES, actualRoles);
        verify(commonServiceMock, timeout(TIMES_ONE)).getAuthoritiesByAuthentication(any());
        verifyNoMoreInteractions(roleDaoMock, authenticationMock, userDetailsMock);
    }

    /**
     * Add user role correct user id and role id correct.
     */
    @Test
    public void addUserRole_correctUserIdAndRoleId_correct() {
        when(roleDaoMock.addUserRole(any(), any())).thenReturn(Boolean.TRUE);
        roleService.addUserRole(any(), any());
        verify(roleDaoMock, timeout(TIMES_ONE)).addUserRole(any(), any());
        verifyNoMoreInteractions(roleDaoMock, authenticationMock, userDetailsMock, commonServiceMock);
    }

    /**
     * Add user role correct user id and role id validation exception.
     */
    @Test(expected = ValidationException.class)
    public void addUserRole_correctUserIdAndRoleId_validationException() {
        when(roleDaoMock.addUserRole(any(), any())).thenReturn(Boolean.FALSE);
        roleService.addUserRole(any(), any());
        verify(roleDaoMock, timeout(TIMES_ONE)).addUserRole(any(), any());
        verifyNoMoreInteractions(roleDaoMock, authenticationMock, userDetailsMock, commonServiceMock);
    }

    /**
     * Gets roles by granted authorities correct granted authorities correct roles returned.
     */
    @Test
    public void getRolesByGrantedAuthorities_correctGrantedAuthorities_correctRolesReturned() {
        Set<String> actualRolesByGrantedAuthorities = roleService.getRolesByGrantedAuthorities(CORRECT_GRANTED_AUTHORITY);
        assertEquals(CORRECT_ROLES, actualRolesByGrantedAuthorities);
        verifyNoMoreInteractions(roleDaoMock, authenticationMock, userDetailsMock, commonServiceMock);
    }

}
