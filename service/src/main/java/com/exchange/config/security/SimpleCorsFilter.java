package com.exchange.config.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Simple cors filter.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter("/*")
public class SimpleCorsFilter implements Filter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ASTERISK = "*";
    private static final String TRUE = "true";
    private static final String HTTP_METHODS = "POST, GET, OPTIONS, DELETE, PUT";
    private static final String ACCESS_CONTROL_MAX_AGE_VALUE = "3600";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS_VALUE = "Origin, origin, x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
    private static final String OPTIONS = "OPTIONS";

    /**
     * Instantiates a new Simple cors filter.
     */
    public SimpleCorsFilter() {
    }

    @Override
    public void init(final FilterConfig fc) {
    }

    @Override
    public void doFilter(
            final ServletRequest req,
            final ServletResponse resp,
            final FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ASTERISK);
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, TRUE);
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, HTTP_METHODS);
        response.setHeader(ACCESS_CONTROL_MAX_AGE, ACCESS_CONTROL_MAX_AGE_VALUE);
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_VALUE);

        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }

    }

    @Override
    public void destroy() {
    }
}