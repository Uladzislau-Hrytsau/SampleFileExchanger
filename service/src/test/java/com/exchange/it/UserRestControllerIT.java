package com.exchange.it;

import com.exchange.controller.converter.JsonConverter;
import com.exchange.dao.User;
import com.exchange.dto.user.UserUpdatingDto;
import com.exchange.wrapper.Response;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type User rest controller it.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRestControllerIT {

    private static final String ENDPOINT = "http://localhost:";
    private static final String PORT = "8088";
    private static final String TOKEN_ENDPOINT = "/oauth/token";
    private static final String USER_ENDPOINT = "/users";
    private static final User CORRECT_USER_WITH_ROLE_ADMIN = new User("admin", "1");
    private static final User CORRECT_USER_WITH_ROLE_USER = new User("user", "1");
    private static final User CORRECT_USER = new User("newLogin", "password");
    private static final User INCORRECT_USER = new User("", "");
    private static final UserUpdatingDto CORRECT_UPDATING_USER = new UserUpdatingDto(
            3L,
            "updatedPassword",
            false,
            "updatedInformation",
            LocalDate.of(1999, 9, 9));
    private static final UserUpdatingDto INCORRECT_UPDATING_USER = new UserUpdatingDto();
    private static final Long INCORRECT_USER_ID = -1L;
    private static final String CLIENT_ID_PASSWORD = "clientIdPassword";
    private static final String SECRET = "secret";
    private static final Integer CORRECT_SIZE = 5;
    private static final Integer CORRECT_PAGE = 1;
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders httpHeaders = new HttpHeaders();
    private OAuth2AccessToken token;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    /**
     * Gets users by page and size with correct page and size then correct response returned.
     */
    @Test
    public void getUsersByPageAndSizeWithCorrectPageAndSizeThenCorrectResponseReturned() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("page", CORRECT_PAGE)
                .queryParam("size", CORRECT_SIZE).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Response response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(CORRECT_SIZE, java.util.Optional.of(response.getPagination().getCount().intValue()).get());
        assertEquals(CORRECT_SIZE, java.util.Optional.of(response.getData().size()).get());
    }

    /**
     * Gets users by page and size with role user then throw forbidden.
     */
    @Test(expected = HttpClientErrorException.Forbidden.class)
    public void getUsersByPageAndSizeWithRoleUserThenThrowForbidden() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("page", CORRECT_PAGE)
                .queryParam("size", CORRECT_SIZE).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    /**
     * Gets users by page and size with incorrect page and size then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void getUsersByPageAndSizeWithIncorrectPageAndSizeThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add user with correct user.
     */
    @Test
    public void addUserWithCorrectUser() {
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_USER), httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                Void.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    /**
     * Add user with incorrect user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addUserWithIncorrectUserThenThrowBadRequest() {
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(JsonConverter.asJsonString(INCORRECT_USER), httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add user with already exist user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addUserWithAlreadyExistUserThenThrowBadRequest() {
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_USER_WITH_ROLE_ADMIN), httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add user with non user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addUserWithNonUserThenThrowBadRequest() {
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Update user with correct user updating dto and role admin.
     */
    @Test
    public void updateUserWithCorrectUserUpdatingDtoAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_UPDATING_USER), httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                entity,
                Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Update user with correct user updating dto and role user.
     */
    @Test
    public void updateUserWithCorrectUserUpdatingDtoAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_UPDATING_USER), httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                entity,
                Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Update user with incorrect user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void updateUserWithIncorrectUserThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<String> entity = new HttpEntity<>(JsonConverter.asJsonString(INCORRECT_UPDATING_USER), httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                entity,
                Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Delete user with correct user id and role admin.
     */
    @Test
    public void deleteUserWithCorrectUserIdAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", 3000).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Delete user with correct user id and role user then throw forbidden.
     */
    @Test(expected = HttpClientErrorException.Forbidden.class)
    public void deleteUserWithCorrectUserIdAndRoleUserThenThrowForbidden() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", 4000).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    /**
     * Delete user with correct user id and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void deleteUserWithCorrectUserIdAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(USER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", INCORRECT_USER_ID).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private void getAccessTokenByUser(User user) {
        String endpoint = ENDPOINT.concat(PORT).concat(TOKEN_ENDPOINT);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(CLIENT_ID_PASSWORD, SECRET);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", user.getName());
        body.add("password", user.getPassword());
        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, httpHeaders);
        ResponseEntity<OAuth2AccessToken> responseEntity =
                restTemplate.exchange(endpoint,
                        HttpMethod.POST,
                        entity,
                        OAuth2AccessToken.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        this.token = responseEntity.getBody();
    }

    private void buildTokenHeader() {
        httpHeaders.set("Authorization", token.getTokenType().concat(" ").concat(token.getValue()));
    }
}
