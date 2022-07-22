package br.com.bookstore.accountmanagement.config.feign.decoders;

import br.com.bookstore.exceptions.KeycloakException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String s, Response response) {

        Exception exception = getException(s, response);

        try (var reader = response.body().asInputStream()) {

            br.com.bookstore.dto.KeycloakErrorDTO keycloakErrorDTO = objectMapper.readValue(reader, br.com.bookstore.dto.KeycloakErrorDTO.class);

            return new KeycloakException(exception, response.status(), keycloakErrorDTO);

        } catch (Exception e) {
            return exception;
        }

    }

    private Exception getException(String s, Response response) {
        final ErrorDecoder errorDecoder = new Default();
        return errorDecoder.decode(s, response);
    }
}
