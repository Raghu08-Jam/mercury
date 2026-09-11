package com.mercury.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/customers/all")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/products")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/products")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/products/{id}")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/customers")
                        .permitAll()
                        .anyRequest()
                        .hasRole("CUSTOMER")
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
