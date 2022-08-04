package br.com.bookstore.catalog.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

@Configuration
public class RedisConfig {

    public static final String CACHE_BOOKS = "cache-books";
    public static final String CACHE_BOOK_BY_ID = "cache-book-by-id";
    public static final String CACHE_AUTHOR = "cache-author";

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues();
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(CACHE_BOOKS, defaultCacheConfig().entryTtl(Duration.ofMinutes(5)))
                .withCacheConfiguration(CACHE_BOOK_BY_ID, defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                ;
    }

}
