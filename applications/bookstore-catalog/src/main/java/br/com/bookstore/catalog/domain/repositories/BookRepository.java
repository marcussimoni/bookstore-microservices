package br.com.bookstore.catalog.domain.repositories;

import br.com.bookstore.catalog.domain.entities.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
