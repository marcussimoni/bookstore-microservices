package br.com.bookstore.catalog.services;

import br.com.bookstore.catalog.config.RedisConfig;
import br.com.bookstore.catalog.domain.dtos.BookDTO;
import br.com.bookstore.catalog.domain.entities.Book;
import br.com.bookstore.catalog.domain.repositories.BookRepository;
import br.com.bookstore.dto.BookstoreUserDTO;
import br.com.bookstore.exceptions.BookstoreError;
import br.com.bookstore.exceptions.BookstoreException;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

import static br.com.bookstore.catalog.config.RedisConfig.CACHE_BOOKS;

@Data
@Service
public class BookService {

    private final BookRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    private final AccountManagementService accountManagementService;

    @Cacheable(cacheNames = CACHE_BOOKS)
    public Page<BookDTO> findAll(Pageable page){

        return repository.findAll(page).map(book -> {

            BookDTO bookDTO = mapper.map(book, BookDTO.class);

            BookstoreUserDTO response = accountManagementService.findByUsername(book.getAuthorId());

            bookDTO.setAuthor(response);

            return bookDTO;

        });

    }

    @Cacheable(cacheNames = RedisConfig.CACHE_BOOK_BY_ID)
    public BookDTO findById(long id){

        BookstoreError error = BookstoreError
                .builder()
                .description("Book not found")
                .build();

        Book book = repository
                .findById(id)
                .orElseThrow(() -> new BookstoreException(400, error));

        BookDTO bookDTO = mapper.map(book, BookDTO.class);

        bookDTO.setAuthor(accountManagementService.findByUsername(book.getAuthorId()));

        return bookDTO;

    }

    @Transactional
    public BookDTO save(BookDTO dto, Authentication auth) {

        Book entity = mapper.map(dto, Book.class);

        entity.setAuthorId(auth.getName());

        repository.save(entity);

        BookDTO bookDTO = mapper.map(entity, BookDTO.class);

        bookDTO.setAuthor(accountManagementService.findByUsername(auth.getName()));

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
