package com.company.orderservice.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long externalOrderId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<Product> products;

    private BigDecimal total;
    private String status;

    public Order() {}

    public Order(Long externalOrderId, List<Product> products) {
        this.externalOrderId = externalOrderId;
        this.products = products;
        this.total = BigDecimal.ZERO;
    }

    public void calculateTotal() {
        this.total = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() { return id; }
    public Long getExternalOrderId() { return externalOrderId; }
    public void setExternalOrderId(Long externalOrderId) { this.externalOrderId = externalOrderId; }
    public List<Product> getProducts() { return products; }
    public BigDecimal getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 