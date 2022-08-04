package br.com.bookstore.catalog.clients;

import br.com.bookstore.dto.BookstoreUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "accountManagerClient",
        value = "accountManagerClient",
        url = "${feign.account-management.address}",
        configuration = {AccountManagementInterceptor.class}
)
public interface AccountManagementClient {

    @GetMapping(path = "/user")
    ResponseEntity<BookstoreUserDTO> findByUsername(@RequestParam("username") String username);

}
