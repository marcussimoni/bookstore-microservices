package br.com.bookstore.catalog.services;

import br.com.bookstore.catalog.clients.AccountManagementClient;
import br.com.bookstore.catalog.config.RedisConfig;
import br.com.bookstore.dto.BookstoreUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AccountManagementService {

    @Autowired
    private AccountManagementClient client;

    @Cacheable(cacheNames = RedisConfig.CACHE_AUTHOR)
    public BookstoreUserDTO findByUsername(String username){

        return client.findByUsername(username).getBody();

    }

}
