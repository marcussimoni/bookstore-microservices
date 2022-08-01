package br.com.bookstore.exceptions;

import lombok.Data;

@Data
public class BookstoreException extends RuntimeException {

    public BookstoreException(int status, BookstoreError error) {
        super();
        this.status = status;
        this.error = error;
    }

    private int status;
    private BookstoreError error;

}
