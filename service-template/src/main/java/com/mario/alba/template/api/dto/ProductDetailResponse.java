package com.mario.alba.template.api.dto;

import lombok.Getter;

@Getter
public class ProductDetailResponse extends ProductSummaryResponse {
  private final String description;

  public ProductDetailResponse(
      String productId,
      String sku,
      String name,
      long priceMinor,
      String currency,
      String description) {
    super(productId, sku, name, priceMinor, currency);
    this.description = description;
  }
}
