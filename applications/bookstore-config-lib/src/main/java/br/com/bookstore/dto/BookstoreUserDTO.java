package br.com.bookstore.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookstoreUserDTO implements Serializable {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
