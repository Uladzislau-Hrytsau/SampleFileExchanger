package com.exchange.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * The type Web application initializer.
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String URL_MAPPING = "/";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        return new Class<?>[]{WebConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        System.out.println("WebApplicationInitializer");
        return new String[]{URL_MAPPING};
    }
}
