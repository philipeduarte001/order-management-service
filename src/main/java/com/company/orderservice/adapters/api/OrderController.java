package com.company.orderservice.adapters.api;

import com.company.orderservice.application.OrderService;
import com.company.orderservice.domain.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/process/{externalOrderId}")
    public ResponseEntity<Order> processOrder(@PathVariable Long externalOrderId) {
        Order order = orderService.processOrderFromA(externalOrderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
} 