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

    @Test
    void construtorPadraoDeveInicializarProduct() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    void gettersDevemRetornarValoresCorretos() {
        Product product = new Product(2L, "Outro Produto", new BigDecimal("50.00"));
        assertEquals(2L, product.getId());
        assertEquals("Outro Produto", product.getName());
        assertEquals(new BigDecimal("50.00"), product.getPrice());
    }
} 