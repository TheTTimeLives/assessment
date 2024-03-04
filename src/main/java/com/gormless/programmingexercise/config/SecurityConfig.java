package com.gormless.programmingexercise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // you wouldn't do this typically as this sets no auth for the whole app
        // but I figure setting up JWT based auth is a bit outside of the scope of this assessment
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll() // This line permits all requests
                )
                .csrf().disable(); // Disabling CSRF protection, be cautious with this in production
        return http.build();
    }
}
