package br.com.bookstore.accountmanagement;

import br.com.bookstore.accountmanagement.config.testcontainer.PostgresTestContainer;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAuthTokenRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {
        PostgresTestContainer.Initializer.class
})
public class JacksonTest extends PostgresTestContainer {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test() throws JsonProcessingException {

        var request = new KeycloakAuthTokenRequestDTO();

        request.setClient_secret("clientSecret");
        request.setClient_id("clientId");
        request.setScope("scope");

        System.out.println(objectMapper.writeValueAsString(request));


    }

}
