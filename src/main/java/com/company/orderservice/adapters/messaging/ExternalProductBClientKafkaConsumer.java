package com.company.orderservice.adapters.messaging;

import com.company.orderservice.application.OrderService;
import com.company.orderservice.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ExternalProductBClientKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ExternalProductBClientKafkaConsumer.class);
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public ExternalProductBClientKafkaConsumer(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "pedidos-sistema-b", groupId = "order-service-group")
    public void consume(String message) {
        try {
            logger.info("Recebendo pedido do Sistema B: {}", message);
            Order order = objectMapper.readValue(message, Order.class);
            Order processedOrder = orderService.processOrderFromB(order.getId());
            logger.info("Pedido processado com sucesso: {}", processedOrder.getId());
        } catch (Exception e) {
            logger.error("Erro ao processar pedido do Sistema B: {}", message, e);
        }
    }
} 