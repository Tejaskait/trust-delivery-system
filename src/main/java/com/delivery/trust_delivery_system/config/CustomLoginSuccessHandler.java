package com.delivery.trust_delivery_system.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        // Converts the authorities list into a Set of strings for easier checking
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        
        String redirectUrl = "/login?error";

        // Logic to prioritize and redirect based on role
        if (roles.contains("ROLE_ADMIN")) {
            redirectUrl = "/admin/home";
        } else if (roles.contains("ROLE_DELIVERY")) {
            redirectUrl = "/delivery/home";
        } else if (roles.contains("ROLE_CUSTOMER")) {
            redirectUrl = "/customer/home";
        }

        // Log for debugging (you'll see this in your IDE console)
        System.out.println("User authenticated. Roles: " + roles + " -> Redirecting to: " + redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}