package br.com.bookstore.catalog.clients;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class AccountManagementInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Jwt jwt = getJwt(authentication);

            String authorizationToken = getAuthorizationToken(jwt);

            requestTemplate.header("Authorization", authorizationToken);
        };

    }


    private Jwt getJwt(Authentication auth) {
        Jwt jwt = (Jwt) auth.getCredentials();
        return jwt;
    }

    private String getAuthorizationToken(Jwt jwt) {
        return "Bearer " + jwt.getTokenValue();
    }

}
