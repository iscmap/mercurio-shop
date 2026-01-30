package com.mario.alba.template.api.dto;

import lombok.Getter;

@Getter
public class ProductSummaryResponse {
  private final String productId;
  private final String sku;
  private final String name;
  private final long priceMinor;
  private final String currency;

  public ProductSummaryResponse(
      String productId, String sku, String name, long priceMinor, String currency) {
    this.productId = productId;
    this.sku = sku;
    this.name = name;
    this.priceMinor = priceMinor;
    this.currency = currency;
  }
}
