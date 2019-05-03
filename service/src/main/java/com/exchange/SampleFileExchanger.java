package com.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;

//@SpringBootApplication(
//        exclude = {org.springframework.boot.autoconfigure.security.servlet.class}
//        exclude = {
//        SecurityAutoConfiguration.class,
//        UserDetailsServiceAutoConfiguration.class,
//        OAuth2AutoConfiguration.class,
//}, scanBasePackageClasses = {
//        OAuth2ServerConfiguration.class,
//        ResourceServerOAuth2Configuration.class,
//        SecurityConfiguration.class,
//        SimpleCorsFilter.class,
//        UserDetailsService.class
//})
//)
@Configuration
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,})
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SpringBootWebSecurityConfiguration.class}))
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.springframework.boot.autoconfigure.security.*"), basePackages = "com.exchange.*")
//@SpringBootApplication
public class SampleFileExchanger {
    public static void main(String[] args) {
        SpringApplication.run(SampleFileExchanger.class, args);
    }
}
