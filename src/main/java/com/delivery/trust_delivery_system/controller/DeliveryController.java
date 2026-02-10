package com.delivery.trust_delivery_system.controller;

import com.delivery.trust_delivery_system.model.*;
import com.delivery.trust_delivery_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderAssignmentRepository assignmentRepository;

    @GetMapping("/home")
public String deliveryHome(Model model, @AuthenticationPrincipal UserDetails currentUser) {
    // 1. Get the user object
    User agent = userRepository.findByUsername(currentUser.getUsername()).orElseThrow();

    // 2. Fetch data (Ensure these return empty lists, not null)
    List<Order> available = orderRepository.findByStatus(Order.OrderStatus.PLACED);
    List<OrderAssignment> myTasks = assignmentRepository.findByDeliveryAgentAndStatusNot(
            agent, OrderAssignment.AssignmentStatus.DELIVERED);

    // 3. Force initialize if null (Extra safety for the Thymeleaf error)
    if (available == null) available = new java.util.ArrayList<>();
    if (myTasks == null) myTasks = new java.util.ArrayList<>();

    // 4. Add to model - The names MUST match the HTML exactly
    model.addAttribute("availableOrders", available);
    model.addAttribute("myTasks", myTasks);
    
    return "delivery_home";
}

    @PostMapping("/accept-order/{orderId}")
    public String acceptOrder(@PathVariable Long orderId, @AuthenticationPrincipal UserDetails currentUser) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        User agent = userRepository.findByUsername(currentUser.getUsername()).orElseThrow();

        // 1. Update Order status
        order.setStatus(Order.OrderStatus.OUT_FOR_DELIVERY);
        orderRepository.save(order);

        // 2. Create the Assignment record
        OrderAssignment assignment = new OrderAssignment();
        assignment.setOrder(order);
        assignment.setDeliveryAgent(agent);
        assignment.setStatus(OrderAssignment.AssignmentStatus.ACCEPTED);
        assignmentRepository.save(assignment);

        return "redirect:/delivery/home?accepted";
    }
}