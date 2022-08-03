package br.com.bookstore.catalog.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.com.bookstore.catalog.clients")
public class OpenFeignConfig {
}
