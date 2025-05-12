package com.company.orderservice.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void deveCriarProdutoComValoresCorretos() {
        Product product = new Product(1L, "Produto Teste", new BigDecimal("99.99"));
        assertEquals(1L, product.getId());
        assertEquals("Produto Teste", product.getName());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
    }
} 