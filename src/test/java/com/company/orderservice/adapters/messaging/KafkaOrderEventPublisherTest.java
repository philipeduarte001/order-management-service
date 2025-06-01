package com.company.orderservice.adapters.messaging;

import com.company.orderservice.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class KafkaOrderEventPublisherTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;
    private KafkaOrderEventPublisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new KafkaOrderEventPublisher(kafkaTemplate, objectMapper);
    }

    @Test
    void publishOrderProcessed_sucesso() throws Exception {
        Order order = mock(Order.class);
        when(objectMapper.writeValueAsString(order)).thenReturn("orderJson");
        publisher.publishOrderProcessed(order);
        verify(kafkaTemplate).send("order-processed", "orderJson");
    }

    @Test
    void publishOrderProcessed_excecao() throws Exception {
        Order order = mock(Order.class);
        when(objectMapper.writeValueAsString(order)).thenThrow(new RuntimeException("erro"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> publisher.publishOrderProcessed(order));
        assertTrue(ex.getMessage().contains("Erro ao publicar evento de pedido processado"));
    }
} 