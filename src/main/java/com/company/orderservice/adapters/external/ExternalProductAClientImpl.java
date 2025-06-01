package com.company.orderservice.adapters.external;

import com.company.orderservice.domain.model.Order;
import com.company.orderservice.domain.model.Product;
import com.company.orderservice.domain.ports.ExternalProductAClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class ExternalProductAClientImpl implements ExternalProductAClient {
    @Override
    public Order fetchOrder(Long externalOrderId) {
        return new Order(externalOrderId, Arrays.asList(
                new Product(1L, "Produto 1", new BigDecimal("10.00")),
                new Product(2L, "Produto 2", new BigDecimal("20.00"))
        ));
    }
} 