package com.mario.alba.template.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderResponse {
  private final String orderId;
  private final String status;

  public CreateOrderResponse(String orderId, String status) {
    this.orderId = orderId;
    this.status = status;
  }
}
