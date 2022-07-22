package br.com.bookstore.accountmanagement.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakAuthTokenRequestDTO implements Serializable {

    @JsonProperty("client_secret")
    protected String client_secret;

    @JsonProperty("client_id")
    protected String client_id;

    @JsonProperty("grant_type")
    protected String grant_type;

    protected String scope;

    private String username;
    private String password;

}
