package br.com.bookstore.accountmanagement.services;

import br.com.bookstore.accountmanagement.domain.dtos.CredentialsDTO;
import br.com.bookstore.accountmanagement.domain.dtos.KeycloakUserDTO;
import br.com.bookstore.accountmanagement.domain.dtos.SignUpDTO;
import br.com.bookstore.accountmanagement.domain.entities.BookstoreUser;
import br.com.bookstore.accountmanagement.domain.repositories.UserRepository;
import br.com.bookstore.dto.BookstoreUserDTO;
import br.com.bookstore.exceptions.BookstoreError;
import br.com.bookstore.exceptions.BookstoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper;

    private final KeycloakService keycloakService;

    private final UserRepository repository;

    private final JmsTemplate jmsTemplate;

    @Value("${queue-names.delete-user}")
    private String queueDeleteUser;



    public void signUp(SignUpDTO signUpDTO){

        KeycloakUserDTO keycloakUserDTO = userDtoBuilder(signUpDTO);

        KeycloakUserDTO user = keycloakService.findByUsername(keycloakUserDTO);

        if(Objects.nonNull(user)){
            throw new RuntimeException("Username already exists");
        }

        keycloakService.createNewUser(keycloakUserDTO);

        user = keycloakService.findByUsername(keycloakUserDTO);

        try{

            BookstoreUser entity = mapper.map(keycloakUserDTO, BookstoreUser.class);

            entity.setKeycloakId(user.getId());

            repository.save(entity);

        } catch (Exception e) {
            log.error("Error while saving user into database", e);

            jmsTemplate.convertAndSend(queueDeleteUser, user);

            throw new RuntimeException(e);
        }

    }

    public KeycloakUserDTO userDtoBuilder(SignUpDTO signUpDTO) {

        CredentialsDTO credentialDTO = CredentialsDTO
                .builder()
                .type("password")
                .value(signUpDTO.getPassword())
                .temporary(false)
                .build();

        return KeycloakUserDTO
                .builder()
                .enabled(true)
                .email(signUpDTO.getEmail())
                .emailVerified(false)
                .username(signUpDTO.getUsername())
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .credentials(List.of(credentialDTO))
                .build();
    }

    public void delete(KeycloakUserDTO dto) {

        KeycloakUserDTO user = keycloakService.findByUsername(dto);

        if(Objects.nonNull(user)){

            BookstoreUser entity = mapper.map(user, BookstoreUser.class);

            repository.delete(entity);

        } else {

            log.info("User not found or already deleted from database");

        }

    }

    public BookstoreUserDTO findByUsername(String username) {

        BookstoreUser user = repository.findByUsername(username).orElseThrow(() -> new BookstoreException(400, BookstoreError.builder().error("Customer not found").build()));

        return mapper.map(user, BookstoreUserDTO.class);

    }
}
