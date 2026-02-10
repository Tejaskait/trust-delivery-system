package com.delivery.trust_delivery_system.controller;

import com.delivery.trust_delivery_system.model.Order;
import com.delivery.trust_delivery_system.model.User;
import com.delivery.trust_delivery_system.repository.OrderRepository;
import com.delivery.trust_delivery_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute Order order, @AuthenticationPrincipal UserDetails currentUser) {
        // Step 2 Logic: Find the logged-in user object
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow();
        
        // Link order to customer
        order.setCustomer(user);
        order.setStatus(Order.OrderStatus.PLACED);
        
        orderRepository.save(order);
        return "redirect:/customer/home?orderSuccess";
    }
}