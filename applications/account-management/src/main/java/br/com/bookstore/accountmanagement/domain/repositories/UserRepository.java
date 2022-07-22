package br.com.bookstore.accountmanagement.domain.repositories;

import br.com.bookstore.accountmanagement.domain.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}