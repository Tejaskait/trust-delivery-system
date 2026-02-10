package com.delivery.trust_delivery_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/customer/home")
    public String customerHome() {
        return "customer_home"; 
    }

  
}