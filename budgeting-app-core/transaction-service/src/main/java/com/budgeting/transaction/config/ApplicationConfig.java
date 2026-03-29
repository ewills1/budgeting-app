package com.budgeting.transaction.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * The transaction-service validates JWTs issued by the user-service.
     * We only need a minimal UserDetailsService to satisfy the filter chain;
     * the JWT itself carries the principal's username (email).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.withUsername(username)
                .password("")
                .roles("USER")
                .build();
    }
}
