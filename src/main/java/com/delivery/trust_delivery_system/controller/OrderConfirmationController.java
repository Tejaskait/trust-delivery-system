package com.delivery.trust_delivery_system.controller;

import com.delivery.trust_delivery_system.model.*;
import com.delivery.trust_delivery_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/order/confirmation")
public class OrderConfirmationController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderConfirmationRepository confirmationRepository;

    @GetMapping("/{orderId}")
    public String showConfirmation(@PathVariable Long orderId, Model model) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        Optional<OrderConfirmation> confirmationOpt = confirmationRepository.findByOrderId(orderId);

        model.addAttribute("order", order);
        confirmationOpt.ifPresent(c -> model.addAttribute("conf", c));
        
        return "order_confirmation";
    }

    @PostMapping("/tick")
    public String handleTick(@RequestParam Long orderId, @AuthenticationPrincipal UserDetails userDetails) {
        OrderConfirmation oc = confirmationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Confirmation record not found."));
        
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_CUSTOMER")) {
            oc.setCustomerConfirmed(true);
        } else if (role.equals("ROLE_DELIVERY")) {
            // STRICT LOGIC: Delivery man can only tick IF customer has already ticked
            if (!oc.isCustomerConfirmed()) {
                throw new RuntimeException("Security Protocol: Customer must mark 'Parcel Received' before you can mark 'Delivered'.");
            }
            oc.setDeliveryConfirmed(true);
        }

        confirmationRepository.save(oc);

        if (oc.isCustomerConfirmed() && oc.isDeliveryConfirmed()) {
            Order order = oc.getOrder();
            order.setStatus(Order.OrderStatus.COMPLETED);
            orderRepository.save(order);
        }

        return "redirect:/order/confirmation/" + orderId;
    }
}