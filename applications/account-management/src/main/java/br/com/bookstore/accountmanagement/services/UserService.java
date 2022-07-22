package br.com.bookstore.accountmanagement.services;

import br.com.bookstore.accountmanagement.domain.dtos.UserDTO;
import br.com.bookstore.accountmanagement.domain.entities.User;
import br.com.bookstore.accountmanagement.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    public UserDTO findByUsername(UserDTO userDTO){

        List<UserDTO> users = keycloakService.findByUsername(userDTO.getUsername());

        return CollectionUtils.isEmpty(users) ? null : users.get(0);

    }

    public void signUp(UserDTO userDTO){

        UserDTO user = findByUsername(userDTO);

        if(Objects.nonNull(user)){
            throw new RuntimeException("Username already exists");
        }

        keycloakService.createNewUser(userDTO);

        user = findByUsername(userDTO);

        try{

            User entity = mapper.map(userDTO, User.class);

            entity.setKeycloakId(user.getId());

            repository.save(entity);

        } catch (Exception e) {
            log.error("Error while saving user into database", e);

            jmsTemplate.convertAndSend(queueDeleteUser, user);

            throw new RuntimeException(e);
        }

    }

    public void delete(UserDTO dto) {

        UserDTO user = findByUsername(dto);

        if(Objects.nonNull(user)){

            User entity = mapper.map(user, User.class);

            repository.delete(entity);

        } else {

            log.info("User not found or already deleted from database");

        }

    }

}
