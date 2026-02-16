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
        
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        
        String redirectUrl = "/login?error";

        // Logic updated to check for raw database strings (ADMIN, DELIVERY, CUSTOMER)
        if (roles.contains("ADMIN") || roles.contains("ROLE_ADMIN")) {
            redirectUrl = "/admin/home";
        } else if (roles.contains("DELIVERY") || roles.contains("ROLE_DELIVERY")) {
            redirectUrl = "/delivery/home";
        } else if (roles.contains("CUSTOMER") || roles.contains("ROLE_CUSTOMER")) {
            redirectUrl = "/customer/home";
        }

        System.out.println("User authenticated. Roles: " + roles + " -> Redirecting to: " + redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}