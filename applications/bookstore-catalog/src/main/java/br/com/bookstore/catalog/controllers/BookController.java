package br.com.bookstore.catalog.controllers;

import br.com.bookstore.catalog.domain.dtos.BookDTO;
import br.com.bookstore.catalog.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    public Page<BookDTO> listAll(Pageable page){

        return service.findAll(page);

    }

    @GetMapping(path = "{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable("id") long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> save(@RequestBody @Valid BookDTO dto, Principal principal){
        return new ResponseEntity<>(service.save(dto, principal), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<BookDTO> update(@PathVariable("id") long id, @RequestBody @Valid BookDTO dto, Principal principal){
        return new ResponseEntity<>(service.update(id, dto, principal), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id){
        service.delete(id);
    }

}
