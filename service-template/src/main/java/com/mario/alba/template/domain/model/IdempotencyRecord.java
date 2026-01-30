package com.mario.alba.template.domain.model;

import static com.mario.alba.common.util.Preconditions.requireNotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IdempotencyRecord {
  private final String key;
  private final String requestHash;
  private final String orderId;
  private final String status;

  public IdempotencyRecord(String key, String requestHash, String orderId, String status) {
    this.key = requireNotBlank(key, "key");
    this.requestHash = requireNotBlank(requestHash, "requestHash");
    this.orderId = requireNotBlank(orderId, "orderId");
    this.status = requireNotBlank(status, "status");
  }
}
