package com.mario.alba.template.adapters.memory;

import com.mario.alba.template.domain.model.OrderRecord;
import com.mario.alba.template.domain.ports.OrderRepositoryPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepositoryAdapter implements OrderRepositoryPort {

  private final ConcurrentHashMap<String, OrderRecord> store =
      new ConcurrentHashMap<String, OrderRecord>();

  @Override
  public void save(OrderRecord order) {
    store.put(order.getOrderId(), order);
  }

  @Override
  public Optional<OrderRecord> findById(String orderId) {
    return Optional.ofNullable(store.get(orderId));
  }

  @Override
  public List<OrderRecord> findAll() {
    return new ArrayList<OrderRecord>(store.values());
  }
}
