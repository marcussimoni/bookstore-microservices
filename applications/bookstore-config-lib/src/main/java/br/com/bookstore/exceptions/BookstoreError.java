package br.com.bookstore.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookstoreError {

    private String description;
    private String message;
    private String error;

}
