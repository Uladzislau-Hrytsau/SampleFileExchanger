package com.exchange.service;

import com.exchange.configuration.security.userdetails.UserDetails;
import com.exchange.dao.UserDetailsDao;
import com.exchange.dto.security.UserDetailsDto;
import com.exchange.exception.ValidationException;
import com.exchange.service.implementation.UserDetailsServiceImpl;
import com.exchange.service.validation.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The type User details service impl mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Long CORRECT_USER_ID = 1L;
    private static final String CORRECT_NAME = "correctUserName";
    private static final String CORRECT_PASSWORD = "correctUserPassword";
    private static final String CORRECT_ADMIN_ROLE = "ROLE_ADMIN";
    private static final String CORRECT_USER_ROLE = "ROLE_USER";
    private static final Collection<GrantedAuthority> CORRECT_GRANTED_AUTHORITY = new HashSet<>(Arrays.asList(
            new SimpleGrantedAuthority(CORRECT_ADMIN_ROLE),
            new SimpleGrantedAuthority(CORRECT_USER_ROLE)));
    private static final Set<String> CORRECT_ROLES = new HashSet<>(Arrays.asList(
            CORRECT_ADMIN_ROLE,
            CORRECT_USER_ROLE));
    private static final UserDetailsDto CORRECT_USER_DETAILS_DTO = new UserDetailsDto(
            CORRECT_USER_ID,
            CORRECT_NAME,
            CORRECT_PASSWORD,
            CORRECT_ROLES);

    @Mock
    private UserDetailsDao userDetailsDaoMock;
    @Mock
    private UserValidator userValidatorMock;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Load user by username correct login correct user details returned.
     */
    @Test
    public void loadUserByUsername_correctLogin_correctUserDetailsReturned() {
        doNothing().when(userValidatorMock).validateLogin(any());
        when(userDetailsDaoMock.getUserDetailsByLogin(any())).thenReturn(CORRECT_USER_DETAILS_DTO);
        UserDetails actualUserDetails = (UserDetails) userDetailsService.loadUserByUsername(CORRECT_NAME);
        UserDetails expectedUserDetails = new UserDetails(CORRECT_NAME, CORRECT_PASSWORD, CORRECT_GRANTED_AUTHORITY);
        expectedUserDetails.setUserId(CORRECT_USER_ID);
        assertEquals(expectedUserDetails, actualUserDetails);
        verify(userValidatorMock, times(TIMES_ONE)).validateLogin(any());
        verify(userDetailsDaoMock, timeout(TIMES_ONE)).getUserDetailsByLogin(any());
        verifyNoMoreInteractions(userDetailsDaoMock, userValidatorMock);
    }

    /**
     * Load user by username incorrect login validation exception.
     */
    @Test(expected = ValidationException.class)
    public void loadUserByUsername_incorrectLogin_validationException() {
        doThrow(ValidationException.class).when(userValidatorMock).validateLogin(any());
        userDetailsService.loadUserByUsername(any());
        verify(userValidatorMock, times(TIMES_ONE)).validateLogin(any());
        verify(userDetailsDaoMock, never()).getUserDetailsByLogin(any());
        verifyNoMoreInteractions(userDetailsDaoMock, userValidatorMock);
    }

    /**
     * Gets granted authorities by user details dto correct user details dto correct granted authorities.
     */
    @Test
    public void getGrantedAuthoritiesByUserDetailsDto_correctUserDetailsDto_correctGrantedAuthorities() {
        Set<GrantedAuthority> actualGrantedAuthoritiesByUserDetailsDto =
                userDetailsService.getGrantedAuthoritiesByUserDetailsDto(CORRECT_USER_DETAILS_DTO);
        assertEquals(CORRECT_GRANTED_AUTHORITY, actualGrantedAuthoritiesByUserDetailsDto);
    }

}
