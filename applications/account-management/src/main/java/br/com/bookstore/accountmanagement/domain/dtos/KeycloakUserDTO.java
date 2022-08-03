package br.com.bookstore.accountmanagement.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class KeycloakUserDTO implements Serializable {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<CredentialsDTO> credentials;
    private boolean emailVerified;
    private long createdTimestamp;
    private boolean enabled;
    private boolean totp;
    private int notBefore;
    private AccessDTO access;

}
