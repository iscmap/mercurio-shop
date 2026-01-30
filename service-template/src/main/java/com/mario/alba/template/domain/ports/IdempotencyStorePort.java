package com.mario.alba.template.domain.ports;

import com.mario.alba.template.domain.model.IdempotencyRecord;
import java.util.Optional;

public interface IdempotencyStorePort {
  Optional<IdempotencyRecord> findByKey(String key);

  void save(IdempotencyRecord record);
}
