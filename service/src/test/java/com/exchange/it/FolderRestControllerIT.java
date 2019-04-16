package com.exchange.it;

import com.exchange.controller.converter.JsonConverter;
import com.exchange.dao.User;
import com.exchange.dto.folder.FolderStructureDto;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type Folder rest controller it.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FolderRestControllerIT {

    private static final String ENDPOINT = "http://localhost:";
    private static final String PORT = "8088";
    private static final String TOKEN_ENDPOINT = "/oauth/token";
    private static final String FOLDER_ENDPOINT = "/folders";
    private static final String CLIENT_ID_PASSWORD = "clientIdPassword";
    private static final String SECRET = "secret";
    private static final User CORRECT_USER_WITH_ROLE_ADMIN = new User("admin", "1");
    private static final User CORRECT_USER_WITH_ROLE_USER = new User("user", "1");
    private static final FolderStructureDto CORRECT_FOLDER_STRUCTURE_DTO = new FolderStructureDto(5L, "name");
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private OAuth2AccessToken token;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        CORRECT_FOLDER_STRUCTURE_DTO.setId(4L);
        CORRECT_FOLDER_STRUCTURE_DTO.setName("name");

    }

    /**
     * Add folder with role admin.
     */
    @Test
    public void addFolderWithRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FOLDER_STRUCTURE_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    /**
     * Add folder with role user.
     */
    @Test
    public void addFolderWithRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FOLDER_STRUCTURE_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    /**
     * Add folder with incorrect folder structure dto then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFolderWithIncorrectFolderStructureDtoThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(null), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Update folder name with correct folder structure dto and role admin.
     */
    @Test
    public void updateFolderNameWithCorrectFolderStructureDtoAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FOLDER_STRUCTURE_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Update folder name with correct folder structure dto and role user.
     */
    @Test
    public void updateFolderNameWithCorrectFolderStructureDtoAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FOLDER_STRUCTURE_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Update folder name with incorrect folder structure dto then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void updateFolderNameWithIncorrectFolderStructureDtoThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(null), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Delete folder with correct folder id and role admin.
     */
    @Test
    public void deleteFolderWithCorrectFolderIdAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", 4000L).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Delete folder with correct folder id and role user.
     */
    @Test
    public void deleteFolderWithCorrectFolderIdAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", 5000L).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Delete folder with incorrect folder id by user id then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void deleteFolderWithIncorrectFolderIdByUserIdThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", 4001L).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Delete folder with physical files not exist then throw internal server error.
     */
    @Test(expected = HttpServerErrorException.InternalServerError.class)
    public void deleteFolderWithPhysicalFilesNotExistThenThrowInternalServerError() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FOLDER_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", 1L).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
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
