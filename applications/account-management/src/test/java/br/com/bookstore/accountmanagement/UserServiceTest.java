package br.com.bookstore.accountmanagement;

import br.com.bookstore.accountmanagement.config.testcontainer.PostgresTestContainer;
import br.com.bookstore.accountmanagement.domain.dtos.UserDTO;
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
public class UserServiceTest extends PostgresTestContainer {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void mustSignUp() throws JsonProcessingException {

        String json = getPayload();

        UserDTO userDTO = objectMapper.readValue(json, UserDTO.class);

        userService.signUp(userDTO);

        UserDTO client = userService.findByUsername(userDTO);

        Assertions.assertEquals(userDTO.getUsername(), client.getUsername());

    }

    @NotNull
    private String getPayload() {
        return "{\n" +
                "    \"enabled\": true,\n" +
                "    \"username\": \"bookstore\",\n" +
                "    \"email\": \"bookstore@bookstore.com\",\n" +
                "    \"firstName\": \"first name\",\n" +
                "    \"lastName\": \"last name\",\n" +
                "    \"credentials\": [\n" +
                "        {\n" +
                "            \"type\": \"password\",\n" +
                "            \"value\": \"123456\",\n" +
                "            \"temporary\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"emailVerified\": false,\n" +
                "    \"requiredActions\": [\n" +
                "        \"CONFIGURE_TOTP\",\n" +
                "        \"VEFITY_EMAIL\"\n" +
                "    ],\n" +
                "    \"groups\": [],\n" +
                "    \"attributes\": {\n" +
                "        \"locale\": [\"en\"]\n" +
                "    }   \n" +
                "}";
    }

}
