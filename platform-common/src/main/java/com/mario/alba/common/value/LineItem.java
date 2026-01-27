package com.mario.alba.common.value;

import static com.mario.alba.common.util.Preconditions.requireNotBlank;

public class LineItem {
  private final String sku;
  private final int quantity;
  private final long unitPriceMinor;
  private final String currency;

  public LineItem(String sku, int quantity, long unitPriceMinor, String currency) {
    this.sku = requireNotBlank(sku, "sku");
    if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    if (unitPriceMinor < 0) throw new IllegalArgumentException("unitPriceMinor must be >= 0");
    this.quantity = quantity;
    this.unitPriceMinor = unitPriceMinor;
    this.currency = requireNotBlank(currency, "currency");
  }

  public String sku() {
    return sku;
  }

  public int quantity() {
    return quantity;
  }

  public long unitPriceMinor() {
    return unitPriceMinor;
  }

  public String currency() {
    return currency;
  }
}
