package com.company.orderservice.application;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.model.Product;
import com.company.orderservice.domain.ports.OrderRepository;
import com.company.orderservice.domain.ports.OrderEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Order processOrderFromA(Long externalOrderId) {
        Order existing = orderRepository.findByExternalOrderId(externalOrderId);
        if (existing != null) {
            return existing;
        }
        Order order = new Order(externalOrderId, List.of(
            new Product(null, "Produto A", new BigDecimal("100.00")),
            new Product(null, "Produto B", new BigDecimal("200.00"))
        ));
        
        order.calculateTotal();

        orderRepository.save(order);

        eventPublisher.publishOrderProcessed(order);
        
        return order;
    }

    @Transactional
    public Order processOrderFromB(Long externalOrderId) {
        Order existing = orderRepository.findByExternalOrderId(externalOrderId);
        if (existing != null) {
            return existing;
        }
        Order order = new Order(externalOrderId, List.of(
            new Product(null, "Produto C", new BigDecimal("150.00")),
            new Product(null, "Produto D", new BigDecimal("250.00"))
        ));
        
        order.calculateTotal();

        orderRepository.save(order);

        eventPublisher.publishOrderProcessed(order);
        
        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
} 