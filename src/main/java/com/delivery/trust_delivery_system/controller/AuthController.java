package com.delivery.trust_delivery_system.controller; // Fixed package

import com.delivery.trust_delivery_system.model.User; // Fixed import
import com.delivery.trust_delivery_system.repository.UserRepository; // Fixed import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String password, 
                               @RequestParam String role) {
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(User.Role.valueOf(role));
        user.setActive(true);
        
        userRepository.save(user);
        return "redirect:/login?success"; 
    }
}