package com.mario.alba.template.domain.ports;

import com.mario.alba.template.domain.model.OrderRecord;
import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {

  void save(OrderRecord orderRecord);

  Optional<OrderRecord> findById(String orderId);

  List<OrderRecord> findAll();
}
