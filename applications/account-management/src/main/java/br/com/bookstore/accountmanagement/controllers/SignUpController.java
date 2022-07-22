package br.com.bookstore.accountmanagement.controllers;

import br.com.bookstore.accountmanagement.domain.dtos.UserDTO;
import br.com.bookstore.accountmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("sign-up")
public class SignUpController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void signUp(@Valid @RequestBody UserDTO user){
        userService.signUp(user);
    }

}
