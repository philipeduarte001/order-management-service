package com.company.orderservice.adapters.repository;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderRepositoryJpaImplTest {
    private SpringDataOrderRepository springDataOrderRepository;
    private OrderRepositoryJpaImpl orderRepositoryJpaImpl;

    @BeforeEach
    void setUp() {
        springDataOrderRepository = mock(SpringDataOrderRepository.class);
        orderRepositoryJpaImpl = new OrderRepositoryJpaImpl(springDataOrderRepository);
    }

    @Test
    void save_deveChamarSpringDataSave() {
        Order order = new Order(1L, Arrays.asList(
                new Product(1L, "Produto 1", new BigDecimal("10.00")),
                new Product(2L, "Produto 2", new BigDecimal("20.00"))
        ));
        orderRepositoryJpaImpl.save(order);
        verify(springDataOrderRepository, times(1)).save(order);
    }

    @Test
    void findAll_deveRetornarListaDePedidos() {
        List<Order> orders = Collections.singletonList(new Order(1L, Collections.emptyList()));
        when(springDataOrderRepository.findAll()).thenReturn(orders);
        List<Order> result = orderRepositoryJpaImpl.findAll();
        assertEquals(orders, result);
    }
} 