package com.mario.alba.template.config;

import com.mario.alba.template.adapters.memory.InMemoryIdempotencyStoreAdapter;
import com.mario.alba.template.adapters.memory.InMemoryOrderRepositoryAdapter;
import com.mario.alba.template.application.OrderApplicationService;
import com.mario.alba.template.domain.ports.IdempotencyStorePort;
import com.mario.alba.template.domain.ports.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppWiringConfig {

  @Bean
  public OrderRepositoryPort orderRepositoryPort() {
    return new InMemoryOrderRepositoryAdapter();
  }

  @Bean
  public IdempotencyStorePort idempotencyStorePort() {
    return new InMemoryIdempotencyStoreAdapter();
  }

  @Bean
  public OrderApplicationService orderApplicationService(
      OrderRepositoryPort orderRepositoryPort, IdempotencyStorePort idempotencyStorePort) {
    return new OrderApplicationService(orderRepositoryPort, idempotencyStorePort);
  }
}
