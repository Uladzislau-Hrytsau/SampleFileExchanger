package com.exchange.it.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * The type File configuration.
 */
@Configuration
@PropertySource("classpath:error-message.properties")
public class
FileConfigurationIT {

    /**
     * Multipart resolver commons multipart resolver.
     *
     * @return the commons multipart resolver
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(Long.MAX_VALUE);
        return multipartResolver;
    }
}
