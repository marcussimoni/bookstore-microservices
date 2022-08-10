package br.com.bookstore.catalog.domain.dtos;

import br.com.bookstore.dto.BookstoreUserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Serializable {

    private long id;

    @NotBlank
    private String publisher;

    @NotBlank
    private String title;

    @NotNull
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private LocalDate releasedAt;

    @NotNull
    @Positive
    private Integer numberOfPages;

    private BookstoreUserDTO author;

}
