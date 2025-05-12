package com.company.orderservice.adapters.external;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.ports.ExternalProductBClient;
import org.springframework.stereotype.Component;

@Component
public class ExternalProductBClientImpl implements ExternalProductBClient {
    @Override
    public void sendOrder(Order order) {
        System.out.println("Pedido enviado para o sistema B: " + order.getId() + ", total: " + order.getTotal());
    }
} 