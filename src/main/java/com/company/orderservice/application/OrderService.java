package com.company.orderservice.application;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.ports.OrderRepository;
import com.company.orderservice.domain.ports.OrderEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    private final ProductCreationService productCreationService;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher eventPublisher, ProductCreationService productCreationService) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.productCreationService = productCreationService;
    }

    @Transactional
    public Order processOrderFromA(Long externalOrderId) {
        Order existing = orderRepository.findByExternalOrderId(externalOrderId);
        if (existing != null) {
            return existing;
        }
        
        Order order = new Order(externalOrderId, productCreationService.createProductsForSystemA());
        order.setStatus(productCreationService.getDefaultReceivedStatus());
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
        
        Order order = new Order(externalOrderId, productCreationService.createProductsForSystemB());
        order.setStatus(productCreationService.getDefaultReceivedStatus());
        order.calculateTotal();

        orderRepository.save(order);
        eventPublisher.publishOrderProcessed(order);
        
        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
} 