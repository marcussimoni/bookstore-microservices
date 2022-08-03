package br.com.bookstore.accountmanagement.config.security;

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
                .csrf()
                .disable()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-up/**").permitAll()
                .antMatchers(HttpMethod.POST, "/sign-in/**").permitAll()
                .and()

                .authorizeRequests(auth ->
                    auth
                        .antMatchers(HttpMethod.GET, "/user/**").hasAuthority(USER)
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
