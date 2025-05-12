package com.company.orderservice.domain.ports;

import com.company.orderservice.domain.model.Order;

public interface OrderEventPublisher {
    void publishOrderProcessed(Order order);
} 