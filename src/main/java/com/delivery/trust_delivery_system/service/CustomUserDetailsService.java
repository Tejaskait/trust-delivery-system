package com.delivery.trust_delivery_system.service; // Fixed package

import com.delivery.trust_delivery_system.model.User; // Fixed import
import com.delivery.trust_delivery_system.repository.UserRepository; // Fixed import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // We manually add ROLE_ to match what the SuccessHandler and SecurityConfig expect
    String roleWithPrefix = "ROLE_" + user.getRole().name(); 

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPasswordHash())
        .authorities(roleWithPrefix) // Use authorities instead of roles for total control
        .build();
}
}