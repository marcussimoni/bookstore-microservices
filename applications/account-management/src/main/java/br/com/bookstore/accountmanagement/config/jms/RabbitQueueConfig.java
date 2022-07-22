package br.com.bookstore.accountmanagement.config.jms;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Bean
    public Queue createUserQueue(@Value("${queue-names.delete-user}") String name){
        return new Queue(name);
    }

}
