package br.com.bookstore.accountmanagement.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TB_USER")
@SequenceGenerator(name = "SQ_USER", sequenceName = "SQ_USER",initialValue = 1, allocationSize = 1)
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "SQ_USER", strategy = GenerationType.SEQUENCE)
    private long id;

    private String keycloakId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
