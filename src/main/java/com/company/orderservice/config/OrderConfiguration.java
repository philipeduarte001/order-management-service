package com.company.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "order")
public class OrderConfiguration {
    
    private ProductDefaults productDefaults;
    private StatusDefaults statusDefaults;
    
    public static class ProductDefaults {
        private List<ProductTemplate> systemA;
        private List<ProductTemplate> systemB;
        
        public List<ProductTemplate> getSystemA() { return systemA; }
        public void setSystemA(List<ProductTemplate> systemA) { this.systemA = systemA; }
        public List<ProductTemplate> getSystemB() { return systemB; }
        public void setSystemB(List<ProductTemplate> systemB) { this.systemB = systemB; }
    }
    
    public static class StatusDefaults {
        private String received;
        private String processed;
        
        public String getReceived() { return received; }
        public void setReceived(String received) { this.received = received; }
        public String getProcessed() { return processed; }
        public void setProcessed(String processed) { this.processed = processed; }
    }
    
    public static class ProductTemplate {
        private String name;
        private BigDecimal price;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
    
    public ProductDefaults getProductDefaults() { return productDefaults; }
    public void setProductDefaults(ProductDefaults productDefaults) { this.productDefaults = productDefaults; }
    public StatusDefaults getStatusDefaults() { return statusDefaults; }
    public void setStatusDefaults(StatusDefaults statusDefaults) { this.statusDefaults = statusDefaults; }
} 