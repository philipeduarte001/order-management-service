package com.company.orderservice.adapters.repository;

import com.company.orderservice.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataOrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByExternalOrderId(Long id);
} 