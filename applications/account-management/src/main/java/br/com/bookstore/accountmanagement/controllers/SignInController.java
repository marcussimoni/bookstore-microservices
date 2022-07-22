package br.com.bookstore.accountmanagement.controllers;

import br.com.bookstore.accountmanagement.domain.dtos.KeycloakAccessTokenDTO;
import br.com.bookstore.accountmanagement.domain.dtos.LoginDTO;
import br.com.bookstore.accountmanagement.services.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("sign-in")
public class SignInController {

    private final KeycloakService keycloakService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public KeycloakAccessTokenDTO signIn(@Valid @RequestBody LoginDTO login){
        return keycloakService.signIn(login);
    }

}
