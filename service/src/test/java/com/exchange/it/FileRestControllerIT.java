package com.exchange.it;

import com.exchange.dao.User;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.wrapper.Response;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileRestControllerIT {

    private static final Integer FILE_UPLOAD_COUNT = 5;
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
    private static final FolderStructureDto CORRECT_FOLDER_STRUCTURE_DTO = new FolderStructureDto(4L, "name");
    private static final FileDto CORRECT_FILE_DTO = new FileDto(
            1L,
            1L,
            "description",
            "realName",
            "encodeName",
            LocalDate.of(1000, 10, 10), new HashSet<>(Arrays.asList(1L, 2L)));
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private static final String REPOSITORY_PATH = "/home/vlad/IdeaProjects/SampleFileExchanger/service/src/main/webapp/WEB-INF/repo/";

    static {
        CORRECT_FILE_DTO.setUserId(1L);
        CORRECT_FILE_DTO.setFolderId(1L);
        CORRECT_FILE_DTO.setDescription("description");
        CORRECT_FILE_DTO.setRealName("realName");
        CORRECT_FILE_DTO.setEncodeName("encodeName");
        CORRECT_FILE_DTO.setDate(LocalDate.of(1000, 10, 10));
        CORRECT_FILE_DTO.setCategories(new HashSet<>(Arrays.asList(1L, 2L)));
    }

    private OAuth2AccessToken token;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException {
        System.out.println("setUpBeforeClass");
        System.out.println("setUpBeforeClass");
        System.out.println("setUpBeforeClass");
        System.out.println("setUpBeforeClass");

//        FileRestControllerIT fileRestControllerIT = new FileRestControllerIT();
//
//        fileRestControllerIT.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
//        fileRestControllerIT.setUp();
//        fileRestControllerIT.buildTokenHeader();

//        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);

        ///

//        fileRestControllerIT.loadFiles(FILE_UPLOAD_COUNT);

        ///

//        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
//        URI uri = UriBuilder.fromUri(endpoint)
//                .queryParam("page", CORRECT_PAGE)
//                .queryParam("size", CORRECT_SIZE).build();
//        ResponseEntity<Response> responseEntity = restTemplate.exchange(
//                uri, HttpMethod.GET, httpEntity, Response.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        List<com.exchange.dao.File> data = responseEntity.getBody().getData();
//        List<com.exchange.dao.File> files = data.stream()
//                .filter(item -> "prefix".contains(item.getRealName()))
//                .collect(Collectors.toList());
//
//        System.out.println("=============");
//        System.out.println("=============");
//        System.out.println("=============");
//        files.forEach(System.out::println);
//        System.out.println("=============");
//        System.out.println("=============");
//        System.out.println("=============");


        System.out.println("afterSetUpBeforeClass");
        System.out.println("afterSetUpBeforeClass");
        System.out.println("afterSetUpBeforeClass");
        System.out.println("afterSetUpBeforeClass");
        System.out.println("afterSetUpBeforeClass");
    }

    private void loadFiles(int fileCount) throws IOException {
        this.getAccessTokenByUser(CORRECT_USER_WITH_ROLE_ADMIN);
        this.buildTokenHeader();
        FileSystemResource file;
        String endpoint = ENDPOINT.concat(PORT).concat(FILES_ENDPOINT);
        LinkedMultiValueMap<String, Object> body;
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity;
        ResponseEntity<Void> responseEntity;

        for (int i = 0; i < fileCount; i++) {
            file = new FileSystemResource(this.getTempFile());
            body = new LinkedMultiValueMap<>();
            body.add("multipartFile", file);
            body.add("metaData", CORRECT_FILE_DTO);
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            httpEntity = new HttpEntity<>(body, httpHeaders);
            responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity, Void.class);
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }
    }

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.resetFileDto();
    }

    @Test
    @Ignore
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
        assertEquals(CORRECT_SIZE, java.util.Optional.of(responseEntity.getBody().getPagination().getCount().intValue()).get());
        assertEquals(CORRECT_SIZE, java.util.Optional.of(responseEntity.getBody().getData().size()).get());
    }

    @Test(expected = HttpClientErrorException.Forbidden.class)
    @Ignore
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

    @Test(expected = HttpClientErrorException.BadRequest.class)
    @Ignore
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

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addFileWithIncorrectDescriptionAndRoleUserThenThrowBadRequest() throws IOException {
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

    private void resetFileDto() {
        CORRECT_FILE_DTO.setUserId(1L);
        CORRECT_FILE_DTO.setFolderId(1L);
        CORRECT_FILE_DTO.setDescription("description");
        CORRECT_FILE_DTO.setRealName("realName");
        CORRECT_FILE_DTO.setEncodeName("encodeName");
        CORRECT_FILE_DTO.setDate(LocalDate.of(1000, 10, 10));
        CORRECT_FILE_DTO.setCategories(new HashSet<>(Arrays.asList(1L, 2L)));
    }

    private File getTempFile() throws IOException {
        return File.createTempFile(
                "prefix-",
                "-suffix",
                new File(REPOSITORY_PATH));
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
