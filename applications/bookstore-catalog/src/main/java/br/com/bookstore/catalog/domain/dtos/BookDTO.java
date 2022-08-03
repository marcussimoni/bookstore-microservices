package br.com.bookstore.catalog.domain.dtos;

import br.com.bookstore.dto.BookstoreUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Serializable {

    private long id;

    private BookstoreUserDTO author;

    @NotBlank
    private String publisher;

    @NotBlank
    private String title;

    @NotNull
    private LocalDate releasedAt;

    @NotNull
    @Positive
    private Integer numberOfPages;

}
