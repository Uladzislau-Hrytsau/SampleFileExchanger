package com.exchange.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * The type O auth 2 server configuration.
 */
@Configuration
@EnableAuthorizationServer
class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String PERMIT_ALL = "permitAll()";
    private static final String IS_AUTHENTICATED = "isAuthenticated()";
    private static final String CLIENT_ID_PASSWORD = "clientIdPassword";
    private static final String SECRET = "secret";
    private static final String PASSWORD_GRANT_TYPE = "password";
    private static final String AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String READ_SCOPE = "read";
    private static final String WRITE_SCOPE = "write";
    private static final boolean AUTO_APPROVE = true;

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    /**
     * Instantiates a new O auth 2 server configuration.
     *
     * @param authenticationManager the authentication manager
     * @param userDetailsService    the user details service
     */
    @Autowired
    public OAuth2ServerConfiguration(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess(PERMIT_ALL)
                .checkTokenAccess(IS_AUTHENTICATED);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(CLIENT_ID_PASSWORD)
                .secret(SECRET)
                .authorizedGrantTypes(PASSWORD_GRANT_TYPE, AUTHORIZATION_CODE_GRANT_TYPE, REFRESH_TOKEN_GRANT_TYPE)
                .scopes(READ_SCOPE, WRITE_SCOPE)
                .autoApprove(AUTO_APPROVE);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    /**
     * Token store token store.
     *
     * @return the token store
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}
