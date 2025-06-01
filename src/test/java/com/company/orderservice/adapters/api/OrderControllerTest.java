package com.company.orderservice.adapters.api;

import com.company.orderservice.application.OrderService;
import com.company.orderservice.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processOrder_deveRetornarOrder() {
        Order order = mock(Order.class);
        when(orderService.processOrderFromA(1L)).thenReturn(order);
        ResponseEntity<Order> response = orderController.processOrder(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(order, response.getBody());
    }

    @Test
    void getAllOrders_deveRetornarListaDeOrders() {
        List<Order> orders = Arrays.asList(mock(Order.class), mock(Order.class));
        when(orderService.getAllOrders()).thenReturn(orders);
        ResponseEntity<List<Order>> response = orderController.getAllOrders();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orders, response.getBody());
    }
} 