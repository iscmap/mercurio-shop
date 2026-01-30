package com.mario.alba.template.domain.model;

import com.mario.alba.common.value.Money;
import com.mario.alba.common.value.Quantity;
import com.mario.alba.common.value.Sku;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class OrderLine {
  private final Sku sku;
  private final Quantity quantity;
  private final Money unitPrice;

  public OrderLine(Sku sku, Quantity quantity, Money unitPrice) {
    this.sku = Objects.requireNonNull(sku, "sku");
    this.quantity = Objects.requireNonNull(quantity, "quantity");
    this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice");
  }
}
