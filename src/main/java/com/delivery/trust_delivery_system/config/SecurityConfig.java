package com.delivery.trust_delivery_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // This creates the bean that AuthController is looking for
        return new BCryptPasswordEncoder();
    }

@Autowired
private CustomLoginSuccessHandler successHandler;

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/signup", "/css/**").permitAll()
            .requestMatchers("/customer/**").hasRole("CUSTOMER")
            .requestMatchers("/delivery/**").hasRole("DELIVERY")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .successHandler(successHandler) // Use the custom handler here
            .permitAll()
        );
    return http.build();
}
}