package br.com.bookstore.site.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class BookDTO implements Serializable {

    private String author;
    private String publisher;
    private String title;
    private LocalDate releasedAt;
    private int numberOfPages;

}
