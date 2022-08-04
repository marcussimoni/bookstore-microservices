package br.com.bookstore.accountmanagement.controllers;

import br.com.bookstore.accountmanagement.services.UserService;
import br.com.bookstore.dto.BookstoreUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "authenticated")
    public ResponseEntity<BookstoreUserDTO> autenticatedUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        BookstoreUserDTO dto = userService.findByUsername(auth.getName());

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<BookstoreUserDTO> findByUsername(@RequestParam("username") String username){

        BookstoreUserDTO dto = userService.findByUsername(username);

        return ResponseEntity.ok(dto);

    }

}
