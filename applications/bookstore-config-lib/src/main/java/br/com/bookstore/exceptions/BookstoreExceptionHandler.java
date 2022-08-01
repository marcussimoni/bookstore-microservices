package br.com.bookstore.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BookstoreExceptionHandler {

    @ResponseBody
    @ExceptionHandler({KeycloakException.class})
    public ResponseEntity<BookstoreError> keycloakExceptionHandler(Exception exception){

        KeycloakException keycloakException = (KeycloakException) exception;

        BookstoreError error = BookstoreError
                .builder()
                .message(keycloakException.getError().getErrorMessage())
                .error(keycloakException.getError().getError())
                .description(keycloakException.getError().getErrorDescription())
                .status(keycloakException.getStatus())
                .build();

        return ResponseEntity.status(keycloakException.getStatus()).body(error);

    }

    @ResponseBody
    @ExceptionHandler({BookstoreException.class})
    public ResponseEntity<BookstoreException> bookstoreExceptionHandler(Exception exception){

        BookstoreException bookstoreException = (BookstoreException) exception;

        return ResponseEntity.status(bookstoreException.getStatus()).body(bookstoreException);

    }

}
