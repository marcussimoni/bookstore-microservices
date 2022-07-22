package br.com.bookstore.site.controllers;

import br.com.bookstore.site.domain.dtos.BookDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @GetMapping
    public List<BookDTO> listAll(){

        BookDTO book = BookDTO
                .builder()
                .releasedAt(LocalDate.now())
                .title("Spring boot microservices")
                .author("Spring boot author")
                .numberOfPages(300)
                .publisher("Book publisher")
                .build();

        return List.of(book);
    }

}
