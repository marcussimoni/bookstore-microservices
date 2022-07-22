package br.com.bookstore.accountmanagement.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO implements Serializable {

    private String type;
    private String value;
    private boolean temporary;

}
