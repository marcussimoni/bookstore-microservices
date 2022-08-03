package br.com.bookstore.catalog.services;

import br.com.bookstore.catalog.clients.AccountManagerClient;
import br.com.bookstore.catalog.domain.dtos.BookDTO;
import br.com.bookstore.catalog.domain.entities.Book;
import br.com.bookstore.catalog.domain.repositories.BookRepository;
import br.com.bookstore.dto.BookstoreUserDTO;
import br.com.bookstore.exceptions.BookstoreError;
import br.com.bookstore.exceptions.BookstoreException;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Data
@Service
public class BookService {

    private final BookRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    private final AccountManagerClient accountManagerClient;

    public Page<BookDTO> findAll(Pageable page, Authentication auth){

        Jwt jwt = getJwt(auth);

        return repository.findAll(page).map(book -> {

            BookDTO bookDTO = mapper.map(book, BookDTO.class);

            ResponseEntity<BookstoreUserDTO> response = accountManagerClient.findByUsername(book.getAuthorId(), getAuthorizationToken(jwt));

            bookDTO.setAuthor(response.getBody());

            return bookDTO;

        });

    }

    private static Jwt getJwt(Authentication auth) {
        Jwt jwt = (Jwt) auth.getCredentials();
        return jwt;
    }

    private static String getAuthorizationToken(Jwt jwt) {
        return "Bearer " + jwt.getTokenValue();
    }

    public BookDTO findById(long id){

        BookstoreError error = BookstoreError
                .builder()
                .description("Book not found")
                .build();

        Book book = repository
                .findById(id)
                .orElseThrow(() -> new BookstoreException(400, error));

        return mapper.map(book, BookDTO.class);

    }

    @Transactional
    public BookDTO save(BookDTO dto, Authentication auth) {

        Book entity = mapper.map(dto, Book.class);

        Jwt jwt = getJwt(auth);

        entity.setAuthorId(auth.getName());

        repository.save(entity);

        BookDTO bookDTO = mapper.map(entity, BookDTO.class);

        bookDTO.setAuthor(accountManagerClient.findByUsername(auth.getName(), getAuthorizationToken(jwt)).getBody());

        return bookDTO;

    }

    public BookDTO update(long id, BookDTO dto, Principal principal) {

        BookDTO book = findById(id);

        book.setPublisher(dto.getPublisher());
        book.setTitle(dto.getTitle());
        book.setNumberOfPages(dto.getNumberOfPages());
        book.setReleasedAt(dto.getReleasedAt());

        Book entity = mapper.map(book, Book.class);
        entity.setAuthorId(principal.getName());

        repository.save(entity);

        return dto;

    }

    public void delete(long id){

        BookDTO dto = findById(id);

        Book book = mapper.map(dto, Book.class);
        book.setActive(false);

        repository.save(book);

    }

}
