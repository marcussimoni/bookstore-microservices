package br.com.bookstore.accountmanagement.config.feign.interceptors;

import br.com.bookstore.accountmanagement.services.KeycloakAuthorizationService;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakAuthorizationInterceptor {

    private final KeycloakAuthorizationService service;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", service.authenticate());
        };
    }

}
