package com.company.orderservice.adapters.messaging;

import com.company.orderservice.application.OrderService;
import com.company.orderservice.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ExternalProductAClientKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ExternalProductAClientKafkaConsumer.class);
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public ExternalProductAClientKafkaConsumer(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    static class ExternalOrderIdDTO {
        public Long externalOrderId;
        public Long getExternalOrderId() { return externalOrderId; }
        public void setExternalOrderId(Long externalOrderId) { this.externalOrderId = externalOrderId; }
    }

    @KafkaListener(topics = "pedidos-sistema-a", groupId = "order-service-group")
    public void consume(String message) {
        try {
            logger.info("Recebendo pedido do Sistema A: {}", message);
            ExternalOrderIdDTO dto = objectMapper.readValue(message, ExternalOrderIdDTO.class);
            Order processedOrder = orderService.processOrderFromA(dto.getExternalOrderId());
            logger.info("Pedido processado com sucesso: {}", processedOrder.getId());
        } catch (Exception e) {
            logger.error("Erro ao processar pedido do Sistema A: {}", message, e);
        }
    }
} 