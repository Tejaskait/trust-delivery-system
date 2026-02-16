package com.delivery.trust_delivery_system.controller;

import com.delivery.trust_delivery_system.model.Order;
import com.delivery.trust_delivery_system.model.User;
import com.delivery.trust_delivery_system.repository.OrderRepository;
import com.delivery.trust_delivery_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class OrderController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/home")
    public String customerHome(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow();
        List<Order> myOrders = orderRepository.findByCustomer(user);
        model.addAttribute("myOrders", myOrders);
        return "customer_home";
    }

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute Order order, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow();
        order.setCustomer(user);
        order.setStatus(Order.OrderStatus.PLACED);
        Order savedOrder = orderRepository.save(order);
        return "redirect:/order/confirmation/" + savedOrder.getId();
    }
}