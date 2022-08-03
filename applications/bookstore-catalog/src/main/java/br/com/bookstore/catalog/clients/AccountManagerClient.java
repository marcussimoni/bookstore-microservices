package br.com.bookstore.catalog.clients;

import br.com.bookstore.dto.BookstoreUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "accountManagerClient",
    url = "${feign.account-manager.address}"
)
public interface AccountManagerClient {

    @GetMapping(path = "/user")
    ResponseEntity<BookstoreUserDTO> findByUsername(@RequestParam("username") String username, @RequestHeader("Authorization") String authorization);

}
