package com.mario.alba.template.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
  private final String orderId;
  private final String status;
  private final List<OrderLineSummary> items;

  public OrderResponse(String orderId, String status, List<OrderLineSummary> items) {
    this.orderId = orderId;
    this.status = status;
    this.items = items;
  }
}
