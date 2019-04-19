package com.exchange.it;

import com.exchange.dao.User;
import com.exchange.dto.category.CategoryDto;
import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.dto.structure.StructureDto;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type Structure rest controller it.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class StructureRestControllerIT {

    private static final String ENDPOINT = "http://localhost:";
    private static final String PORT = "8088";
    private static final String TOKEN_ENDPOINT = "/oauth/token";
    private static final String STRUCTURE_ENDPOINT = "/structures";
    private static final String CLIENT_ID_PASSWORD = "clientIdPassword";
    private static final String SECRET = "secret";
    private static final User CORRECT_USER_WITH_ROLE_ADMIN = new User("admin", "1");
    private static final User CORRECT_USER_WITH_ROLE_USER = new User("user", "1");
    private static final List<FileStructureDto> CORRECT_FILE_STRUCTURE_DTOS_BY_ROLE_ADMIN = Arrays.asList(
            new FileStructureDto(1L, 1L, "real_name1"),
            new FileStructureDto(2L, 1L, "real_name2"),
            new FileStructureDto(5L, 1L, "real_name5"));
    private static final List<FileStructureDto> CORRECT_FILE_STRUCTURE_DTOS_BY_ROLE_USER = Collections.emptyList();
    private static final List<FolderStructureDto> CORRECT_FOLDER_STRUCTURE_DTOS_BY_ROLE_ADMIN = Collections.singletonList(
            new FolderStructureDto(3L, "folder31"));
    private static final List<FolderStructureDto> CORRECT_FOLDER_STRUCTURE_DTOS_BY_ROLE_USER = Collections.emptyList();
    private static final List<CategoryDto> CORRECT_CATEGORY_DTOS_BY_ROLE_ADMIN = Arrays.asList(
            new CategoryDto(1L, "default"),
            new CategoryDto(2L, "work"),
            new CategoryDto(3L, "home"));
    private static final List<CategoryDto> CORRECT_CATEGORY_DTOS_BY_ROLE_USER = Arrays.asList(
            new CategoryDto(1L, "default"),
            new CategoryDto(2L, "work"));
    private static final StructureDto CORRECT_STRUCTURE_DTO_BY_ROLE_ADMIN = new StructureDto(
            CORRECT_FILE_STRUCTURE_DTOS_BY_ROLE_ADMIN,
            CORRECT_FOLDER_STRUCTURE_DTOS_BY_ROLE_ADMIN,
            CORRECT_CATEGORY_DTOS_BY_ROLE_ADMIN);
    private static final StructureDto CORRECT_STRUCTURE_DTO_BY_ROLE_USER = new StructureDto(
            CORRECT_FILE_STRUCTURE_DTOS_BY_ROLE_USER,
            CORRECT_FOLDER_STRUCTURE_DTOS_BY_ROLE_USER,
            CORRECT_CATEGORY_DTOS_BY_ROLE_USER);
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
     * Gets structure by folder id and categories with correct user id and role admin.
     */
    @Test
    public void getStructureByFolderIdAndCategoriesWithCorrectUserIdAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(STRUCTURE_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", 1).build();
        ResponseEntity<StructureDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, StructureDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CORRECT_STRUCTURE_DTO_BY_ROLE_ADMIN, responseEntity.getBody());
    }

    /**
     * Gets structure by folder id and categories with correct user id and role user.
     */
    @Test
    public void getStructureByFolderIdAndCategoriesWithCorrectUserIdAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(STRUCTURE_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", 1).build();
        ResponseEntity<StructureDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, StructureDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CORRECT_STRUCTURE_DTO_BY_ROLE_USER, responseEntity.getBody());
    }

    /**
     * Gets structure by folder id and categories with incorrect folder id then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void getStructureByFolderIdAndCategoriesWithIncorrectFolderIdThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(STRUCTURE_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("folderId", -1).build();
        ResponseEntity<StructureDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, StructureDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Gets structure by folder id and categories with non folder id then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void getStructureByFolderIdAndCategoriesWithNonFolderIdThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(STRUCTURE_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity<StructureDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, StructureDto.class);
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
