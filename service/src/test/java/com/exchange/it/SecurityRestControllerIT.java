package com.exchange.it;

import com.exchange.dao.User;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type Security rest controller it.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityRestControllerIT {

    private static final String ENDPOINT = "http://localhost:";
    private static final String PORT = "8088";
    private static final String TOKEN_ENDPOINT = "/oauth/token";
    private static final String ROLE_ENDPOINT = "/oauth/role";
    private static final String CLIENT_ID_PASSWORD = "clientIdPassword";
    private static final String SECRET = "secret";
    private static final User CORRECT_USER_WITH_ROLE_ADMIN = new User("admin", "1");
    private static final User CORRECT_USER_WITH_ROLE_USER = new User("user", "1");
    private static final Set<String> ADMIN_ROLES = new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
    private static final Set<String> USER_ROLES = new HashSet<>(Arrays.asList("ROLE_USER"));
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
     * Gets user role with role admin.
     */
    @Test
    public void getUserRoleWithRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(ROLE_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity<Set> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Set.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ADMIN_ROLES, responseEntity.getBody());
    }

    /**
     * Gets user role with role user.
     */
    @Test
    public void getUserRoleWithRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(ROLE_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity<Set> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Set.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(USER_ROLES, responseEntity.getBody());
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
