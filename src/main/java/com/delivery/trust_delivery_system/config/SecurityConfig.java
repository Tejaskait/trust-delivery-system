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

    @Autowired
    private CustomLoginSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Keep this disabled for your POST forms
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                
                // Broadening the matchers to ensure all sub-pages are covered
                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN", "ADMIN")
                .requestMatchers("/delivery/**").hasAnyAuthority("ROLE_DELIVERY", "DELIVERY")
                .requestMatchers("/customer/**").hasAnyAuthority("ROLE_CUSTOMER", "CUSTOMER")
                
                // Explicitly allow the order handoff routes for any authenticated user
                .requestMatchers("/order/**").authenticated() 
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true) // Clears the session on logout
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
            
        return http.build();
    }
}