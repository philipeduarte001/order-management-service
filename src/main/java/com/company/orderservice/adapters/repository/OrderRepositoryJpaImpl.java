package com.company.orderservice.adapters.repository;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.ports.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryJpaImpl implements OrderRepository {
    private final SpringDataOrderRepository repository;

    public OrderRepositoryJpaImpl(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Order order) {
        repository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Order findByExternalOrderId(Long externalOrderId) {
        return repository.findByExternalOrderId(externalOrderId).orElse(null);
    }
} 