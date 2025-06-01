package com.company.orderservice.adapters.external;

import com.company.orderservice.domain.model.Order;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class ExternalProductBClientImplTest {
    @Test
    void sendOrder_naoLancaExcecao() {
        ExternalProductBClientImpl client = new ExternalProductBClientImpl();
        Order order = new Order(1L, Collections.emptyList());
        assertDoesNotThrow(() -> client.sendOrder(order));
    }
} 