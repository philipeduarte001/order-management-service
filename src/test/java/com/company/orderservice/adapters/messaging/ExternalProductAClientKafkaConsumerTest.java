package com.company.orderservice.adapters.messaging;

import com.company.orderservice.application.OrderService;
import com.company.orderservice.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class ExternalProductAClientKafkaConsumerTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OrderService orderService;
    private ExternalProductAClientKafkaConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumer = new ExternalProductAClientKafkaConsumer(objectMapper, orderService);
    }

    @Test
    void consume_mensagemValida_deveProcessarPedido() throws Exception {
        String message = "{\"externalOrderId\":1}";
        ExternalProductAClientKafkaConsumer.ExternalOrderIdDTO dto = new ExternalProductAClientKafkaConsumer.ExternalOrderIdDTO();
        dto.setExternalOrderId(1L);
        when(objectMapper.readValue(message, ExternalProductAClientKafkaConsumer.ExternalOrderIdDTO.class)).thenReturn(dto);
        Order order = mock(Order.class);
        when(orderService.processOrderFromA(1L)).thenReturn(order);
        consumer.consume(message);
        verify(orderService).processOrderFromA(1L);
    }

    @Test
    void consume_mensagemInvalida_naoProcessaPedido() throws Exception {
        String message = "mensagem_invalida";
        when(objectMapper.readValue(anyString(), eq(ExternalProductAClientKafkaConsumer.ExternalOrderIdDTO.class))).thenThrow(new RuntimeException("erro"));
        consumer.consume(message);
        verify(orderService, never()).processOrderFromA(anyLong());
    }
} 