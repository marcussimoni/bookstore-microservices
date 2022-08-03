package br.com.bookstore.accountmanagement.feignclients;

import br.com.bookstore.accountmanagement.config.feign.FeignFormEnconder;
import br.com.bookstore.accountmanagement.config.feign.decoders.KeycloakErrorDecoder;
import br.com.bookstore.accountmanagement.config.feign.interceptors.KeycloakAuthorizationInterceptor;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAccessTokenDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAuthTokenRequestDTO;
import br.com.bookstore.accountmanagement.domain.dtos.LoginDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "user-client",
        value = "user-client",
        url = "${keycloak-server.host}",
        configuration = {KeycloakAuthorizationInterceptor.class, KeycloakErrorDecoder.class, FeignFormEnconder.class}
)
public interface KeycloakClient {

    String BASE_PATH = "/admin/realms/bookstore-realm/users";
    @GetMapping(path = BASE_PATH + "?exact=true")
    List<KeycloakUserDTO> findByUsername(@RequestParam String username);
    @PostMapping(path = BASE_PATH, consumes = "application/json")
    void createNewUser(KeycloakUserDTO keycloakUserDTO);

    @PostMapping(path = "/realms/master/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    KeycloakAccessTokenDTO getAuthToken(@RequestBody KeycloakAuthTokenRequestDTO request);
    @DeleteMapping(path = BASE_PATH + "/{id}")
    void deleteUser(@PathVariable("id") String id);

    @PostMapping(path = "realms/bookstore-realm/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    KeycloakAccessTokenDTO signIn(@RequestBody LoginDTO login);
}
