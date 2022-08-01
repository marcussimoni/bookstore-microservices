package br.com.bookstore.catalog.services;

import br.com.bookstore.catalog.domain.dtos.BookDTO;
import br.com.bookstore.catalog.domain.entities.Book;
import br.com.bookstore.catalog.domain.repositories.BookRepository;
import br.com.bookstore.exceptions.BookstoreError;
import br.com.bookstore.exceptions.BookstoreException;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Data
@Service
public class BookService {

    private final BookRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    public Page<BookDTO> findAll(Pageable page){

        return repository.findAll(page).map(book -> mapper.map(book, BookDTO.class));

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

    public BookDTO save(BookDTO dto) {
        Book entity = mapper.map(dto, Book.class);

        repository.save(entity);

        return mapper.map(entity, BookDTO.class);
    }

    public BookDTO update(long id, BookDTO dto) {

        BookDTO book = findById(id);

        book.setPublisher(dto.getPublisher());
        book.setTitle(dto.getTitle());
        book.setAuthorId(dto.getAuthorId());
        book.setNumberOfPages(dto.getNumberOfPages());
        book.setReleasedAt(dto.getReleasedAt());

        repository.save(mapper.map(book, Book.class));

        return dto;

    }

    public void delete(long id){

        BookDTO dto = findById(id);

        Book book = mapper.map(dto, Book.class);
        book.setActive(false);

        repository.save(book);

    }

}
