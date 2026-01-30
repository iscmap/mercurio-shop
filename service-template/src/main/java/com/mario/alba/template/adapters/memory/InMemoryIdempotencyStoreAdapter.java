package com.mario.alba.template.adapters.memory;

import com.mario.alba.template.domain.model.IdempotencyRecord;
import com.mario.alba.template.domain.ports.IdempotencyStorePort;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryIdempotencyStoreAdapter implements IdempotencyStorePort {

  private final ConcurrentHashMap<String, IdempotencyRecord> store =
      new ConcurrentHashMap<String, IdempotencyRecord>();

  @Override
  public Optional<IdempotencyRecord> findByKey(String key) {
    return Optional.ofNullable(store.get(key));
  }

  @Override
  public void save(IdempotencyRecord record) {
    store.put(record.getKey(), record);
  }
}
