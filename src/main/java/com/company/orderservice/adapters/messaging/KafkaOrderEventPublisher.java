package com.company.orderservice.adapters.messaging;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.ports.OrderEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderEventPublisher implements OrderEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaOrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishOrderProcessed(Order order) {
        try {
            String orderJson = objectMapper.writeValueAsString(order);
            kafkaTemplate.send("order-processed", orderJson);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar evento de pedido processado", e);
        }
    }
} 