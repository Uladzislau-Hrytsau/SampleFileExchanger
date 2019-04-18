package com.exchange.it;

import com.exchange.controller.converter.JsonConverter;
import com.exchange.dao.User;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.wrapper.Response;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The type File rest controller it.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileRestControllerIT {

    private static final String ENDPOINT = "http://localhost:";
    private static final String PORT = "8088";
    private static final String TOKEN_ENDPOINT = "/oauth/token";
    private static final String FILES_ENDPOINT = "/files";
    private static final String CLIENT_ID_PASSWORD = "clientIdPassword";
    private static final String SECRET = "secret";
    private static final Integer CORRECT_PAGE = 1;
    private static final Integer CORRECT_SIZE = 6;
    private static final User CORRECT_USER_WITH_ROLE_ADMIN = new User("admin", "1");
    private static final User CORRECT_USER_WITH_ROLE_USER = new User("user", "1");
    private static final FileDto CORRECT_FILE_DTO = new FileDto(
            1L,
            1L,
            "description",
            "realName",
            "encodeName",
            LocalDate.of(1000, 10, 10), new HashSet<>(Arrays.asList(1L, 2L)));
    private static final FileUpdatingDto CORRECT_FILE_UPDATING_DTO = new FileUpdatingDto(
            1L,
            "updatedDescription",
            "updatedRealName",
            LocalDate.of(1000, 10, 10));
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private static Path tempDirectoryPath;
    private OAuth2AccessToken token;

    /**
     * Prepare environment.
     *
     * @throws IOException the io exception
     */
    @BeforeClass
    public static void prepareEnvironment() throws IOException {
        Path directoryPath = FileSystems.getDefault().getPath("./src/main/webapp/WEB-INF/repo/").normalize().toAbsolutePath();
//        Path directoryPath = FileSystems.getDefault().getPath("/home/travis/build/Uladzislau-Hrytsau/SampleFileExchanger/service/src/main/webapp/WEB-INF/repo/").normalize().toAbsolutePath();
//        tempDirectoryPath = Files.createTempDirectory(directoryPath, "directoryPrefix-");

        tempDirectoryPath = Files.createTempDirectory(directoryPath, "directoryPrefix-");
//        tempDirectoryPath = directoryPath;

//        Files.createDirectory(directoryPath, new )
    }

    /**
     * Clean up.
     *
     * @throws IOException the io exception
     */
    @AfterClass
    public static void cleanUp() throws IOException {
        FileUtils.deleteDirectory(tempDirectoryPath.toFile());


        FileRestControllerIT fileRestControllerIT = new FileRestControllerIT();
        fileRestControllerIT.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        fileRestControllerIT.setUp();
        fileRestControllerIT.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("page", CORRECT_PAGE)
                .queryParam("size", CORRECT_SIZE).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.resetFileDto();
        this.resetFileUpdatingDto();
    }

    /**
     * Gets files by page and size with role admin.
     */
    @Test
    public void getFilesByPageAndSizeWithRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("page", CORRECT_PAGE)
                .queryParam("size", CORRECT_SIZE).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CORRECT_SIZE, java.util.Optional.of(responseEntity.getBody().getData().size()).get());
    }

    /**
     * Gets files by page and size with role user then throw forbidden.
     */
    @Test(expected = HttpClientErrorException.Forbidden.class)
    public void getFilesByPageAndSizeWithRoleUserThenThrowForbidden() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("page", CORRECT_PAGE)
                .queryParam("size", CORRECT_SIZE).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    /**
     * Gets files by page and size with incorrect page and size then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void getFilesByPageAndSizeWithIncorrectPageAndSizeThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.setUp();
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Response.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add file with correct physical file and role admin.
     *
     * @throws IOException the io exception
     */
    @Test
    public void addFileWithCorrectPhysicalFileAndRoleAdmin() throws IOException {
        FileSystemResource file = new FileSystemResource(this.getTempFile());
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    /**
     * Add file with correct physical file and role user.
     *
     * @throws IOException the io exception
     */
    @Test
    public void addFileWithCorrectPhysicalFileAndRoleUser() throws IOException {
        FileSystemResource file = new FileSystemResource(this.getTempFile());
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    /**
     * Add file with incorrect physical file and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectPhysicalFileAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add file with incorrect physical file and role user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectPhysicalFileAndRoleUserThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add file with incorrect file dto and role admin then throw bad request.
     *
     * @throws IOException the io exception
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectFileDtoAndRoleAdminThenThrowBadRequest() throws IOException {
        FileSystemResource file = new FileSystemResource(this.getTempFile());
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add file with incorrect file dto and role user then throw bad request.
     *
     * @throws IOException the io exception
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectFileDtoAndRoleUserThenThrowBadRequest() throws IOException {
        FileSystemResource file = new FileSystemResource(this.getTempFile());
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add file with incorrect description and role admin then throw bad request.
     *
     * @throws IOException the io exception
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectDescriptionAndRoleAdminThenThrowBadRequest() throws IOException {
        CORRECT_FILE_DTO.setDescription("");
        FileSystemResource file = new FileSystemResource(this.getTempFile());
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Add file with incorrect categories and role admin then throw bad request.
     *
     * @throws IOException the io exception
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectCategoriesAndRoleAdminThenThrowBadRequest() throws IOException {
        CORRECT_FILE_DTO.setCategories(new HashSet<>(Arrays.asList(-1L, 2L)));
        FileSystemResource file = new FileSystemResource(this.getTempFile());
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Update file with correct file updating dto and role admin.
     */
    @Test
    public void updateFileWithCorrectFileUpdatingDtoAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        this.setUp();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FILE_UPDATING_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Update file with correct file updating dto and role user.
     */
    @Test
    public void updateFileWithCorrectFileUpdatingDtoAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        this.setUp();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FILE_UPDATING_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Update file with non file updating dto and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void updateFileWithNonFileUpdatingDtoAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        this.setUp();
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Update file with incorrect file id and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void updateFileWithIncorrectFileIdAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        this.setUp();
        CORRECT_FILE_UPDATING_DTO.setId(-1L);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FILE_UPDATING_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Update file with incorrect description and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void updateFileWithIncorrectDescriptionAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        this.setUp();
        CORRECT_FILE_UPDATING_DTO.setDescription("");
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FILE_UPDATING_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Update file with incorrect name and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void updateFileWithIncorrectNameAndRoleAdminThenThrowBadRequest() {
        CORRECT_FILE_UPDATING_DTO.setRealName("");
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(JsonConverter.asJsonString(CORRECT_FILE_UPDATING_DTO), httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Download file with correct file id and file name and role admin then correct physical file returned.
     *
     * @throws IOException the io exception
     */
    @Test
    public void downloadFileWithCorrectFileIdAndFileNameAndRoleAdminThenCorrectPhysicalFileReturned() throws IOException {
        File file = this.loadFile();
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", 6L)
                .queryParam("fileName", file.getName())
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Download file with correct file id and file name and role user then correct physical file returned.
     *
     * @throws IOException the io exception
     */
    @Test
    public void downloadFileWithCorrectFileIdAndFileNameAndRoleUserThenCorrectPhysicalFileReturned() throws IOException {
        File file = this.loadFile();
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", 7L)
                .queryParam("fileName", file.getName())
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Download file with non file id and file name and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void downloadFileWithNonFileIdAndFileNameAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Download file with non file id and file name and role user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void downloadFileWithNonFileIdAndFileNameAndRoleUserThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint).build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Download file with incorrect file id and file name and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void downloadFileWithIncorrectFileIdAndFileNameAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", -6L)
                .queryParam("fileName", "")
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Download file with incorrect file id and file name and role user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void downloadFileWithIncorrectFileIdAndFileNameAndRoleUserThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", -7L)
                .queryParam("fileName", "")
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Remove file with correct id and role admin.
     */
    @Test
    public void removeFileWithCorrectIdAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", 6L)
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Remove file with correct id and role user.
     */
    @Test
    public void removeFileWithCorrectIdAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", 7L)
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Remove file with correct user id and role admin.
     */
    @Test
    public void removeFileWithCorrectUserIdAndRoleAdmin() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", 9L)
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Remove file with correct user id and role user.
     */
    @Test
    public void removeFileWithCorrectUserIdAndRoleUser() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", 10L)
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Remove file with incorrect id and role admin then throw bar request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void removeFileWithIncorrectIdAndRoleAdminThenThrowBarRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", -6L)
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Remove file with incorrect id and role user then throw bar request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void removeFileWithIncorrectIdAndRoleUserThenThrowBarRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("id", -7L)
                .build();
        ResponseEntity responseEntity = restTemplate.exchange(
                uri, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Gets file information by file id with correct file id and role admin then correct file updating dto returned.
     */
    @Test
    public void getFileInformationByFileIdWithCorrectFileIdAndRoleAdminThenCorrectFileUpdatingDtoReturned() {
        FileUpdatingDto actualFileUpdatingDto = new FileUpdatingDto(
                1L, "description1", "real_name1", LocalDate.of(1000, 10, 10));
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", 1L)
                .build();
        ResponseEntity<FileUpdatingDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, FileUpdatingDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), actualFileUpdatingDto);
    }

    /**
     * Gets file information by file id with correct file id and role user then correct file updating dto returned.
     */
    @Test
    public void getFileInformationByFileIdWithCorrectFileIdAndRoleUserThenCorrectFileUpdatingDtoReturned() {
        FileUpdatingDto actualFileUpdatingDto = new FileUpdatingDto(
                4L, "description4", "real_name4", LocalDate.of(1010, 10, 10));
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", 4L)
                .build();
        ResponseEntity<FileUpdatingDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, FileUpdatingDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), actualFileUpdatingDto);
    }

    /**
     * Gets file information by file id with incorrect file id and role admin then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void getFileInformationByFileIdWithIncorrectFileIdAndRoleAdminThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", -4L)
                .build();
        ResponseEntity<FileUpdatingDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, FileUpdatingDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Gets file information by file id with incorrect file id and role user then throw bad request.
     */
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void getFileInformationByFileIdWithIncorrectFileIdAndRoleUserThenThrowBadRequest() {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_USER);
        this.buildTokenHeader();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriBuilder.fromUri(endpoint)
                .queryParam("fileId", -4L)
                .build();
        ResponseEntity<FileUpdatingDto> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, httpEntity, FileUpdatingDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private File loadFile() throws IOException {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        FileSystemResource file;
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body;
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity;
        ResponseEntity<Void> responseEntity;

        File tempFile = this.getTempFile();
        file = new FileSystemResource(tempFile);
        body = new LinkedMultiValueMap<>();
        body.add("multipartFile", file);
        body.add("metaData", CORRECT_FILE_DTO);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpEntity = new HttpEntity<>(body, httpHeaders);
        responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        return tempFile;
    }

    private void resetFileDto() {
        CORRECT_FILE_DTO.setUserId(1L);
        CORRECT_FILE_DTO.setFolderId(1L);
        CORRECT_FILE_DTO.setDescription("description");
        CORRECT_FILE_DTO.setRealName("realName");
        CORRECT_FILE_DTO.setEncodeName("encodeName");
        CORRECT_FILE_DTO.setDate(LocalDate.of(1000, 10, 10));
        CORRECT_FILE_DTO.setCategories(new HashSet<>(Arrays.asList(1L, 2L)));
    }

    private void resetFileUpdatingDto() {
        CORRECT_FILE_UPDATING_DTO.setId(1L);
        CORRECT_FILE_UPDATING_DTO.setDescription("description");
        CORRECT_FILE_UPDATING_DTO.setRealName("realName");
        CORRECT_FILE_UPDATING_DTO.setDate(LocalDate.of(1000, 10, 10));
    }

    private File getTempFile() throws IOException {
        return File.createTempFile(
                "filePrefix-",
                "-fileSuffix",
                new File(tempDirectoryPath.toUri()));
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
