package com.company.orderservice.domain.ports;

import com.company.orderservice.domain.model.Order;
import java.util.List;

public interface OrderRepository {
    void save(Order order);
    Order findById(Long id);
    List<Order> findAll();
    Order findByExternalOrderId(Long externalOrderId);
} 