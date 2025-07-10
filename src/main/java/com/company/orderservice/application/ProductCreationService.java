package com.company.orderservice.application;

import com.company.orderservice.config.OrderConfiguration;
import com.company.orderservice.domain.model.Product;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCreationService {
    
    private final OrderConfiguration orderConfiguration;
    
    public ProductCreationService(OrderConfiguration orderConfiguration) {
        this.orderConfiguration = orderConfiguration;
    }
    
    public List<Product> createProductsForSystemA() {
        return orderConfiguration.getProductDefaults().getSystemA().stream()
                .map(template -> new Product(null, template.getName(), template.getPrice()))
                .collect(Collectors.toList());
    }
    
    public List<Product> createProductsForSystemB() {
        return orderConfiguration.getProductDefaults().getSystemB().stream()
                .map(template -> new Product(null, template.getName(), template.getPrice()))
                .collect(Collectors.toList());
    }
    
    public String getDefaultReceivedStatus() {
        return orderConfiguration.getStatusDefaults().getReceived();
    }
    
    public String getDefaultProcessedStatus() {
        return orderConfiguration.getStatusDefaults().getProcessed();
    }
} 