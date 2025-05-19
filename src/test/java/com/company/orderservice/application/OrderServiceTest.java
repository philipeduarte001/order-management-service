package com.company.orderservice.application;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.model.Product;
import com.company.orderservice.domain.ports.OrderEventPublisher;
import com.company.orderservice.domain.ports.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEventPublisher eventPublisher;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, eventPublisher);
    }

    @Test
    void processOrderFromA_deveProcessarPedidoComSucesso() {
        when(orderRepository.findByExternalOrderId(1L)).thenReturn(null);
        Long externalOrderId = 1L;
        Order order = new Order(externalOrderId, Arrays.asList(
            new Product(1L, "Produto A", new BigDecimal("100.00")),
            new Product(2L, "Produto B", new BigDecimal("200.00"))
        ));
        order.calculateTotal();
        Order result = orderService.processOrderFromA(externalOrderId);
        verify(orderRepository).save(any(Order.class));
        verify(eventPublisher).publishOrderProcessed(any(Order.class));
    }

    @Test
    void processOrderFromA_naoProcessaPedidoDuplicado() {
        Long externalOrderId = 1L;
        Order existingOrder = new Order(externalOrderId, Arrays.asList(
            new Product(1L, "Produto A", new BigDecimal("100.00")),
            new Product(2L, "Produto B", new BigDecimal("200.00"))
        ));
        when(orderRepository.findByExternalOrderId(externalOrderId)).thenReturn(existingOrder);
        Order result = orderService.processOrderFromA(externalOrderId);
        verify(orderRepository, never()).save(any(Order.class));
        verify(eventPublisher, never()).publishOrderProcessed(any(Order.class));
        org.junit.jupiter.api.Assertions.assertEquals(existingOrder, result);
    }

    @Test
    void processOrderFromB_deveProcessarPedidoComSucesso() {
        when(orderRepository.findByExternalOrderId(2L)).thenReturn(null);
        Long externalOrderId = 2L;
        Order result = orderService.processOrderFromB(externalOrderId);
        verify(orderRepository).save(any(Order.class));
        verify(eventPublisher).publishOrderProcessed(any(Order.class));
    }

    @Test
    void processOrderFromB_naoProcessaPedidoDuplicado() {
        Long externalOrderId = 2L;
        Order existingOrder = new Order(externalOrderId, Arrays.asList(
            new Product(3L, "Produto C", new BigDecimal("150.00")),
            new Product(4L, "Produto D", new BigDecimal("250.00"))
        ));
        when(orderRepository.findByExternalOrderId(externalOrderId)).thenReturn(existingOrder);
        Order result = orderService.processOrderFromB(externalOrderId);
        verify(orderRepository, never()).save(any(Order.class));
        verify(eventPublisher, never()).publishOrderProcessed(any(Order.class));
        org.junit.jupiter.api.Assertions.assertEquals(existingOrder, result);
    }

    @Test
    void getAllOrders_deveRetornarListaDePedidos() {
        List<Order> expectedOrders = Arrays.asList(
            new Order(1L, Arrays.asList(new Product(1L, "Produto A", new BigDecimal("100.00")))),
            new Order(2L, Arrays.asList(new Product(2L, "Produto B", new BigDecimal("200.00"))))
        );
        when(orderRepository.findAll()).thenReturn(expectedOrders);
        List<Order> result = orderService.getAllOrders();
        verify(orderRepository).findAll();
    }

    @Test
    void processOrderFromA_deveCalcularTotalCorretamente() {
        when(orderRepository.findByExternalOrderId(10L)).thenReturn(null);
        Long externalOrderId = 10L;
        Order result = orderService.processOrderFromA(externalOrderId);
        BigDecimal expectedTotal = new BigDecimal("300.00");
        org.junit.jupiter.api.Assertions.assertEquals(expectedTotal, result.getTotal());
    }

    @Test
    void processOrderFromB_deveCalcularTotalCorretamente() {
        when(orderRepository.findByExternalOrderId(20L)).thenReturn(null);
        Long externalOrderId = 20L;
        Order result = orderService.processOrderFromB(externalOrderId);
        BigDecimal expectedTotal = new BigDecimal("400.00");
        org.junit.jupiter.api.Assertions.assertEquals(expectedTotal, result.getTotal());
    }
} 