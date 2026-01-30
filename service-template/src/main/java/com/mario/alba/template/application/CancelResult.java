package com.mario.alba.template.application;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelResult {
  private final boolean ok;
  private final boolean notFound;
  private final boolean notAllowed;
  private final String orderId;
  private final String status;
  private final String message;

  private CancelResult(
      boolean ok,
      boolean notFound,
      boolean notAllowed,
      String orderId,
      String status,
      String message) {
    this.ok = ok;
    this.notFound = notFound;
    this.notAllowed = notAllowed;
    this.orderId = orderId;
    this.status = status;
    this.message = message;
  }

  public static CancelResult ok(String orderId, String status) {
    return new CancelResult(true, false, false, orderId, status, null);
  }

  public static CancelResult notFound() {
    return new CancelResult(false, true, false, null, null, "Order not found");
  }

  public static CancelResult notAllowed(String message) {
    return new CancelResult(false, false, true, null, null, message);
  }
}
