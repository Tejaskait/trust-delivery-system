package com.delivery.trust_delivery_system.controller;

import com.delivery.trust_delivery_system.model.*;
import com.delivery.trust_delivery_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderAssignmentRepository assignmentRepository;
    @Autowired private OrderConfirmationRepository confirmationRepository; 

    @GetMapping("/home")
    public String deliveryHome(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User agent = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        List<Order> available = orderRepository.findByStatus(Order.OrderStatus.PLACED);
        
        // Match the status to your AssignmentStatus enum (ACCEPTED, PICKED_UP, DELIVERED)
        List<OrderAssignment> myTasks = assignmentRepository.findByDeliveryAgentAndStatusNot(
                agent, OrderAssignment.AssignmentStatus.DELIVERED);

        model.addAttribute("availableOrders", (available != null) ? available : new ArrayList<>());
        model.addAttribute("myTasks", (myTasks != null) ? myTasks : new ArrayList<>());
        
        return "delivery_home";
    }

    @PostMapping("/accept-order/{orderId}")
    public String acceptOrder(@PathVariable Long orderId, @AuthenticationPrincipal UserDetails userDetails) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        User agent = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        if (order.getStatus() != Order.OrderStatus.PLACED) {
            return "redirect:/delivery/home?error=already_taken";
        }

        // FIX 1: Using "OUT_FOR_DELIVERY" because "ACCEPTED" doesn't exist in OrderStatus
        order.setStatus(Order.OrderStatus.OUT_FOR_DELIVERY);
        orderRepository.save(order);

        // FIX 2: Create the Assignment using AssignmentStatus.ACCEPTED (which DOES exist)
        OrderAssignment assignment = new OrderAssignment();
        assignment.setOrder(order);
        assignment.setDeliveryAgent(agent);
        assignment.setAcceptedAt(LocalDateTime.now());
        assignment.setStatus(OrderAssignment.AssignmentStatus.ACCEPTED);
        assignmentRepository.save(assignment);

        // FIX 3: Initialize the confirmation record
        OrderConfirmation confirmation = new OrderConfirmation();
        confirmation.setOrder(order);
        confirmation.setCustomer(order.getCustomer()); 
        confirmation.setDeliveryAgent(agent);          
        confirmation.setCustomerConfirmed(false);
        confirmation.setDeliveryConfirmed(false);
        confirmationRepository.save(confirmation);

        return "redirect:/delivery/home?accepted=true";
    }
}