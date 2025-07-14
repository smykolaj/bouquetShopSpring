package org.example.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.enums.OrderStatus;
import org.example.finalproject.enums.PaymentMethod;
import org.example.finalproject.enums.PaymentStatus;
import org.example.finalproject.models.Order;
import org.example.finalproject.models.Payment;
import org.example.finalproject.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController
{
    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;

    @GetMapping("/{orderId}")
    public String payPage(@PathVariable Long orderId, Model model)
    {
        Order order = orderRepo.findById(orderId).orElseThrow();
        model.addAttribute("order", order);
        return "payment";
    }

    @PostMapping("/{orderId}/cash")
    public String payCash(@PathVariable Long orderId)
    {
        registerPayment(orderId, PaymentMethod.CASH);
        return "redirect:/payment/thankyou/" + orderId;
    }

    @PostMapping("/{orderId}/card")
    public String payCard(@PathVariable Long orderId)
    {
        registerPayment(orderId, PaymentMethod.CARD);
        return "redirect:/payment/thankyou/" + orderId;
    }

    @GetMapping("/thankyou/{orderId}")
    public String thankYouPage(@PathVariable Long orderId, Model model)
    {
        Order order = orderRepo.findById(orderId).orElseThrow();
        model.addAttribute("order", order);
        return "details";
    }


    private void registerPayment(Long orderId, PaymentMethod method)
    {
        Order order = orderRepo.findById(orderId).orElseThrow();

        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getPrice())
                .method(method)
                .status(PaymentStatus.COMPLETED)
                .build();

        order.setStatus(OrderStatus.PAID);
        order.setPayment(payment);
        order.getCustomer().increaseLoyalty();
        paymentRepo.save(payment);
        orderRepo.save(order);
    }
}
