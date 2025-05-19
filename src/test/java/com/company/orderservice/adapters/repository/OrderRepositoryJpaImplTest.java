package com.company.orderservice.adapters.repository;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Test
    void findById_deveRetornarOrderQuandoPresente() {
        Order order = new Order(1L, Collections.emptyList());
        when(springDataOrderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order result = orderRepositoryJpaImpl.findById(1L);
        assertEquals(order, result);
    }

    @Test
    void findById_deveRetornarNullQuandoNaoPresente() {
        when(springDataOrderRepository.findById(1L)).thenReturn(Optional.empty());
        Order result = orderRepositoryJpaImpl.findById(1L);
        assertNull(result);
    }

    @Test
    void findByExternalOrderId_deveRetornarOrderQuandoPresente() {
        Order order = new Order(99L, Collections.emptyList());
        when(springDataOrderRepository.findByExternalOrderId(99L)).thenReturn(Optional.of(order));
        Order result = orderRepositoryJpaImpl.findByExternalOrderId(99L);
        assertEquals(order, result);
    }

    @Test
    void findByExternalOrderId_deveRetornarNullQuandoNaoPresente() {
        when(springDataOrderRepository.findByExternalOrderId(99L)).thenReturn(Optional.empty());
        Order result = orderRepositoryJpaImpl.findByExternalOrderId(99L);
        assertNull(result);
    }
} 