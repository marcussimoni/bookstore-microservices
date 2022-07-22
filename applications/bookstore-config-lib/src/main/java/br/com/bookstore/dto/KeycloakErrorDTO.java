package br.com.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KeycloakErrorDTO implements Serializable {

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    private String errorMessage;

}
