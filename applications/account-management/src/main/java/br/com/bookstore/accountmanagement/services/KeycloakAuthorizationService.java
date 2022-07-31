package br.com.bookstore.accountmanagement.services;

import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAccessTokenDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAuthTokenRequestDTO;
import br.com.bookstore.accountmanagement.feignclients.KeycloakAuthorizationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakAuthorizationService {

    @Autowired
    private KeycloakAuthorizationClient client;

    @Value("${keycloak-server.user-management-client.password}")
    private String password;

    public String authenticate(){

        var request = new KeycloakAuthTokenRequestDTO();
        request.setClient_id("user-management-client");
        request.setGrant_type("client_credentials");
        request.setClient_secret(password);

        KeycloakAccessTokenDTO authToken = client.getAuthToken(request);

        return "Bearer " + authToken.getAccessToken();

    }
}
