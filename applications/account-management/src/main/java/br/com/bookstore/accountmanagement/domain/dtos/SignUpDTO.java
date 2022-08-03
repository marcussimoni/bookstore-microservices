package br.com.bookstore.accountmanagement.domain.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpDTO implements Serializable {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
