package br.com.bookstore.accountmanagement.domain.repositories;

import br.com.bookstore.accountmanagement.domain.entities.BookstoreUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<BookstoreUser, Long> {
    Optional<BookstoreUser> findByUsername(String username);
}