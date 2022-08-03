package br.com.bookstore.accountmanagement.services;

import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAccessTokenDTO;
import br.com.bookstore.accountmanagement.domain.dtos.LoginDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakUserDTO;
import br.com.bookstore.accountmanagement.feignclients.KeycloakClient;
import br.com.bookstore.exceptions.KeycloakException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    @Autowired
    private KeycloakClient keycloakClient;

    @Value("${keycloak-server.user-management-client.password}")
    private String password;

    private List<KeycloakUserDTO> findByUsername(String username) {
        return keycloakClient.findByUsername(username);
    }

    public KeycloakUserDTO findByUsername(KeycloakUserDTO keycloakUserDTO){

        List<KeycloakUserDTO> users = findByUsername(keycloakUserDTO.getUsername());

        return CollectionUtils.isEmpty(users) ? null : users.get(0);

    }

    public void createNewUser(KeycloakUserDTO keycloakUserDTO) {
        keycloakClient.createNewUser(keycloakUserDTO);
    }

    public void deleteUser(String id) {

        try {

            keycloakClient.deleteUser(id);

        } catch (KeycloakException e) {

            if(e.getStatus() == 404){

                log.info("User not found or already deleted at keycloak server");

            } else {

                throw e;

            }

        }

    }

    public KeycloakAccessTokenDTO signIn(LoginDTO login) {

        login.setClient_id("user-management-client");
        login.setClient_secret(password);
        login.setScope("bookstore-user");
        login.setGrant_type("password");

        return keycloakClient.signIn(login);

    }

}
