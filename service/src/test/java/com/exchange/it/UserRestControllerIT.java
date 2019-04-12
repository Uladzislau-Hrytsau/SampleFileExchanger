package com.exchange.it;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class UserRestControllerIT {

    private static RestTemplate restTemplate = new RestTemplate();
    private String endpoint = "http://localhost:8088/oauth/token";

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
        System.out.println("SETTING UP");
    }

    @Test
    public void getUsersByPageAndSize() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.set("Authorization", "Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=");
        headers.setBasicAuth("clientIdPassword", "clientIdPassword");
//        headers.setBasicAuth("clientIdPassword", "clientIdPassword");
        System.out.println(headers.toString());
        System.out.println(headers.toString());
        System.out.println(headers.toString());
        System.out.println(headers.toString());
        System.out.println(headers.toString());
        System.out.println(headers.toString());
        MultiValueMap<String, String> map =
                new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", "user1");
        map.add("password", "password1");

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<OAuth2AccessToken> response =
                restTemplate.exchange(endpoint,
                        HttpMethod.POST,
                        entity,
                        OAuth2AccessToken.class);

        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
        System.out.println(response.getBody());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBasicAuth("clientIdPassword", "clientIdPassword");
//
//
//        HttpEntity<String> entity = new HttpEntity<>(, headers);
//        ResponseEntity<Response> response = restTemplate.exchange(
//                endpoint,
//                HttpMethod.GET, entity, Response.class);
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println(response.getBody().getTokenType());
        System.out.println(response.getBody().getValue());
        System.out.println(response.getBody().getExpiration());
        System.out.println(response.getBody().getExpiresIn());
        System.out.println(response.getBody().getRefreshToken());
        System.out.println(response.getBody().getScope());
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("ddddddddddddddddddddddddddddddddddddddddd");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        assertEquals(CORRECT_AUTHOR_ID, response.getBody().getId().toString());
    }

//    @Test
//    public void getUsersByPageAndSize() {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//        endpoint += "?page=1&size=5";
//        ResponseEntity<Response> response = restTemplate.exchange(
//                endpoint,
//                HttpMethod.GET, entity, Response.class);
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        System.out.println(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
////        assertEquals(CORRECT_AUTHOR_ID, response.getBody().getId().toString());
//    }

}
