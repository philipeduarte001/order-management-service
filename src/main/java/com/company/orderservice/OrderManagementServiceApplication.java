package com.company.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

// Serviço principal agora consome pedidos do Kafka (pedidos-recebidos) e publica pedidos processados (pedidos-processados)
@SpringBootApplication
@EnableKafka
@EntityScan("com.company.orderservice.domain.model")
@EnableJpaRepositories("com.company.orderservice.adapters.repository")
public class OrderManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementServiceApplication.class, args);
	}

}
