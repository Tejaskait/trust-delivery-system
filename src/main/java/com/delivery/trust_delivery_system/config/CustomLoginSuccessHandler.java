package com.delivery.trust_delivery_system.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        var authorities = authentication.getAuthorities();
        String redirectUrl = "/login?error";

        for (var authority : authorities) {
            if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
                redirectUrl = "/customer/home";
                break;
            } else if (authority.getAuthority().equals("ROLE_DELIVERY")) {
                redirectUrl = "/delivery/home";
                break;
            }
        }
        response.sendRedirect(redirectUrl);
    }
}