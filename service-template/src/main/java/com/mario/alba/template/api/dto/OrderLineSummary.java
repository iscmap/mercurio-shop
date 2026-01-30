package com.mario.alba.template.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderLineSummary {
  private final String sku;
  private final int quantity;

  public OrderLineSummary(String sku, int quantity) {
    this.sku = sku;
    this.quantity = quantity;
  }
}
