package io.javatab.microservices.composite.course.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/openapi/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers(POST, "/course-composite/**").hasAuthority("SCOPE_course:write")
                .pathMatchers(DELETE, "/course-composite/**").hasAuthority("SCOPE_course:write")
                .pathMatchers(GET, "/course-composite/**").hasAuthority("SCOPE_course:read")
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
