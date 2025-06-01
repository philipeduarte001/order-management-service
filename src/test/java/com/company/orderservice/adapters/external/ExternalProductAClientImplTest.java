package com.company.orderservice.adapters.external;

import com.company.orderservice.domain.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExternalProductAClientImplTest {
    @Test
    void fetchOrder_deveRetornarOrderComProdutos() {
        ExternalProductAClientImpl client = new ExternalProductAClientImpl();
        Order order = client.fetchOrder(1L);
        assertNotNull(order);
        assertEquals(1L, order.getExternalOrderId());
        assertNotNull(order.getProducts());
        assertEquals(2, order.getProducts().size());
    }
} 