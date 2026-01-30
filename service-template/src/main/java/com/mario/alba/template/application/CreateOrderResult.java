package com.mario.alba.template.application;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class CreateOrderResult {

  private final boolean success;
  private final boolean duplicate;
  private final boolean conflict;
  private final String orderId;
  private final String status;
  private final String message;

  private CreateOrderResult(
      boolean success,
      boolean duplicate,
      boolean conflict,
      String orderId,
      String status,
      String message) {
    this.success = success;
    this.duplicate = duplicate;
    this.conflict = conflict;
    this.orderId = orderId;
    this.status = status;
    this.message = message;
  }

  public static CreateOrderResult ok(String orderId, String status, boolean duplicate) {
    return new CreateOrderResult(true, duplicate, false, orderId, status, null);
  }

  public static CreateOrderResult conflict(String message) {
    return new CreateOrderResult(false, false, true, null, null, message);
  }
}
