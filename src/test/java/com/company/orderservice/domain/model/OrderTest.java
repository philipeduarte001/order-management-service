package com.company.orderservice.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void calculateTotal_deveSomarValoresDosProdutos() {
        Order order = new Order(1L, Arrays.asList(
                new Product(1L, "Produto 1", new BigDecimal("10.00")),
                new Product(2L, "Produto 2", new BigDecimal("20.00"))
        ));
        order.calculateTotal();
        assertEquals(new BigDecimal("30.00"), order.getTotal());
    }

    @Test
    void calculateTotal_listaVaziaDeveRetornarZero() {
        Order order = new Order(2L, Collections.emptyList());
        order.calculateTotal();
        assertEquals(BigDecimal.ZERO, order.getTotal());
    }

    @Test
    void statusDeveSerAlteradoCorretamente() {
        Order order = new Order(3L, Collections.emptyList());
        order.setStatus("PROCESSED");
        assertEquals("PROCESSED", order.getStatus());
    }

    @Test
    void construtorPadraoDeveInicializarOrder() {
        Order order = new Order();
        assertNotNull(order);
    }

    @Test
    void gettersESettersDevemFuncionarCorretamente() {
        Order order = new Order();
        order.setExternalOrderId(123L);
        order.setStatus("ENVIADO");
        assertNull(order.getId());
        assertEquals(123L, order.getExternalOrderId());
        assertNull(order.getProducts());
        assertNull(order.getTotal());
        assertEquals("ENVIADO", order.getStatus());
    }
} 