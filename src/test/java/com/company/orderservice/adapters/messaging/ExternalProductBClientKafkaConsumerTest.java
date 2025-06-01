package com.company.orderservice.adapters.messaging;

import com.company.orderservice.application.OrderService;
import com.company.orderservice.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class ExternalProductBClientKafkaConsumerTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OrderService orderService;
    private ExternalProductBClientKafkaConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumer = new ExternalProductBClientKafkaConsumer(objectMapper, orderService);
    }

    @Test
    void consume_mensagemValida_deveProcessarPedido() throws Exception {
        String message = "{\"id\":1}";
        Order order = mock(Order.class);
        when(objectMapper.readValue(message, Order.class)).thenReturn(order);
        when(order.getId()).thenReturn(1L);
        Order processedOrder = mock(Order.class);
        when(orderService.processOrderFromB(1L)).thenReturn(processedOrder);
        consumer.consume(message);
        verify(orderService).processOrderFromB(1L);
    }

    @Test
    void consume_mensagemInvalida_naoProcessaPedido() throws Exception {
        String message = "mensagem_invalida";
        when(objectMapper.readValue(anyString(), eq(Order.class))).thenThrow(new RuntimeException("erro"));
        consumer.consume(message);
        verify(orderService, never()).processOrderFromB(anyLong());
    }
} 