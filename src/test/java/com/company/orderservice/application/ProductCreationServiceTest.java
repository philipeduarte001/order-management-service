package com.company.orderservice.application;

import com.company.orderservice.config.OrderConfiguration;
import com.company.orderservice.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCreationServiceTest {

    @Mock
    private OrderConfiguration orderConfiguration;
    
    @Mock
    private OrderConfiguration.ProductDefaults productDefaults;
    
    @Mock
    private OrderConfiguration.StatusDefaults statusDefaults;
    
    @Mock
    private OrderConfiguration.ProductTemplate productTemplate1;
    
    @Mock
    private OrderConfiguration.ProductTemplate productTemplate2;

    private ProductCreationService productCreationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productCreationService = new ProductCreationService(orderConfiguration);
    }

    @Test
    void createProductsForSystemA_deveRetornarProdutosDoSistemaA() {
        when(orderConfiguration.getProductDefaults()).thenReturn(productDefaults);
        when(productDefaults.getSystemA()).thenReturn(List.of(productTemplate1, productTemplate2));
        when(productTemplate1.getName()).thenReturn("Produto A");
        when(productTemplate1.getPrice()).thenReturn(new BigDecimal("100.00"));
        when(productTemplate2.getName()).thenReturn("Produto B");
        when(productTemplate2.getPrice()).thenReturn(new BigDecimal("200.00"));

        List<Product> products = productCreationService.createProductsForSystemA();

        assertEquals(2, products.size());
        assertEquals("Produto A", products.get(0).getName());
        assertEquals(new BigDecimal("100.00"), products.get(0).getPrice());
        assertEquals("Produto B", products.get(1).getName());
        assertEquals(new BigDecimal("200.00"), products.get(1).getPrice());
    }

    @Test
    void createProductsForSystemB_deveRetornarProdutosDoSistemaB() {
        when(orderConfiguration.getProductDefaults()).thenReturn(productDefaults);
        when(productDefaults.getSystemB()).thenReturn(List.of(productTemplate1, productTemplate2));
        when(productTemplate1.getName()).thenReturn("Produto C");
        when(productTemplate1.getPrice()).thenReturn(new BigDecimal("150.00"));
        when(productTemplate2.getName()).thenReturn("Produto D");
        when(productTemplate2.getPrice()).thenReturn(new BigDecimal("250.00"));

        List<Product> products = productCreationService.createProductsForSystemB();

        assertEquals(2, products.size());
        assertEquals("Produto C", products.get(0).getName());
        assertEquals(new BigDecimal("150.00"), products.get(0).getPrice());
        assertEquals("Produto D", products.get(1).getName());
        assertEquals(new BigDecimal("250.00"), products.get(1).getPrice());
    }

    @Test
    void getDefaultReceivedStatus_deveRetornarStatusConfigurado() {
        when(orderConfiguration.getStatusDefaults()).thenReturn(statusDefaults);
        when(statusDefaults.getReceived()).thenReturn("RECEIVED");

        String status = productCreationService.getDefaultReceivedStatus();

        assertEquals("RECEIVED", status);
    }

    @Test
    void getDefaultProcessedStatus_deveRetornarStatusConfigurado() {
        when(orderConfiguration.getStatusDefaults()).thenReturn(statusDefaults);
        when(statusDefaults.getProcessed()).thenReturn("PROCESSED");

        String status = productCreationService.getDefaultProcessedStatus();

        assertEquals("PROCESSED", status);
    }
} 