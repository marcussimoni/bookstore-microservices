package br.com.bookstore.exceptions;

import br.com.bookstore.dto.KeycloakErrorDTO;
import lombok.Data;

@Data
public class KeycloakException extends RuntimeException {

    public KeycloakException(Throwable cause, int status, KeycloakErrorDTO error) {
        super(cause);
        this.status = status;
        this.error = error;
    }

    private int status;
    private KeycloakErrorDTO error;

}
