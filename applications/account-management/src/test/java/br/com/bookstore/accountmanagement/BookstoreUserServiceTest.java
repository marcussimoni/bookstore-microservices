package br.com.bookstore.accountmanagement;

import br.com.bookstore.accountmanagement.config.testcontainer.PostgresTestContainer;
import br.com.bookstore.accountmanagement.domain.dtos.SignUpDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakUserDTO;
import br.com.bookstore.accountmanagement.services.KeycloakService;
import br.com.bookstore.accountmanagement.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {
        PostgresTestContainer.Initializer.class
})
public class BookstoreUserServiceTest extends PostgresTestContainer {

    @Autowired
    private UserService userService;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void mustSignUp() throws JsonProcessingException {

        String json = getPayload();

        SignUpDTO signUpDTO = objectMapper.readValue(json, SignUpDTO.class);

        userService.signUp(signUpDTO);

        KeycloakUserDTO keycloakUserDTO = userService.userDtoBuilder(signUpDTO);

        KeycloakUserDTO client = keycloakService.findByUsername(keycloakUserDTO);

        Assertions.assertEquals(keycloakUserDTO.getUsername(), client.getUsername());

    }

    @NotNull
    private String getPayload() {
        return "{\n" +
                "    \"enabled\": true,\n" +
                "    \"username\": \"bookstore\",\n" +
                "    \"email\": \"bookstore@bookstore.com\",\n" +
                "    \"firstName\": \"first name\",\n" +
                "    \"lastName\": \"last name\",\n" +
                "    \"password\": \"123456\"" +
                "}";
    }

}
