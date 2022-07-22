package br.com.bookstore.site.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = "bookstore-user";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(auth ->
                auth
                    .antMatchers(HttpMethod.GET, "/book/**").hasAuthority(USER)
                    .antMatchers(HttpMethod.POST, "/book/**").hasAuthority(USER)
                    .antMatchers(HttpMethod.PUT, "/book/**").hasAuthority(USER)
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer()
            .jwt().jwtAuthenticationConverter(autheticationConverter())
        ;
    }

    private JwtAuthenticationConverter autheticationConverter() {

        var converter = new JwtGrantedAuthoritiesConverter();

        converter.setAuthoritiesClaimName("scope");
//        converter.setAuthoritiesClaimName("authorities");
        converter.setAuthorityPrefix("");

        var authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);

        return authenticationConverter;

    }

}
