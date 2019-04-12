package com.exchange.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * The type Resource server o auth 2 config.
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerOAuth2Configuration
        extends ResourceServerConfigurerAdapter {

    private static final String OAUTH_TOKEN_ENDPOINT = "/oauth/token";
    private static final String USER_ENDPOINT = "/users";
    private static final String ALL_ENDPOINTS = "/**";
    private static final String HAS_ROLE_ADMIN_OR_HAS_ROLE_USER = "hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')";

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .anonymous().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, OAUTH_TOKEN_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, OAUTH_TOKEN_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.OPTIONS, USER_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, USER_ENDPOINT).permitAll()
                .antMatchers(ALL_ENDPOINTS).access(HAS_ROLE_ADMIN_OR_HAS_ROLE_USER);
    }
}