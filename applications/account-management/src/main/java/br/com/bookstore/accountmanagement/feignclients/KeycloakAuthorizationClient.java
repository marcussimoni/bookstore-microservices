package br.com.bookstore.accountmanagement.feignclients;

import br.com.bookstore.accountmanagement.config.feign.FeignFormEnconder;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAccessTokenDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAuthTokenRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "user-authorization-client",
        value = "user-authorization-client",
        url = "${keycloak-server.host}",
        configuration = FeignFormEnconder.class
)
public interface KeycloakAuthorizationClient {

    @PostMapping(path = "/realms/master/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    KeycloakAccessTokenDTO getAuthToken(KeycloakAuthTokenRequestDTO request);

}
