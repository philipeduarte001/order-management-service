package com.company.orderservice.domain.ports;

import com.company.orderservice.domain.model.Order;

public interface ExternalProductAClient {
    Order fetchOrder(Long externalOrderId);
} 