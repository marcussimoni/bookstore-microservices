package br.com.bookstore.accountmanagement.listeners;

import br.com.bookstore.accountmanagement.domain.dtos.KeycloakUserDTO;
import br.com.bookstore.accountmanagement.services.KeycloakService;
import br.com.bookstore.accountmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {

    private final KeycloakService keycloakService;
    private final UserService userService;

    @JmsListener(destination = "${queue-names.delete-user}")
    public void deleteUser(KeycloakUserDTO user){

        log.info("Delete user at keycloak server");

        keycloakService.deleteUser(user.getId());

        log.info("Delete user at postgres database");

        userService.delete(user);

    }

}
