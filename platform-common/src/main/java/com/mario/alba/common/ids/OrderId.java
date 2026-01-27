package com.mario.alba.common.ids;

import java.util.Objects;
import java.util.UUID;

public class OrderId {

  private final UUID value;

  private OrderId(UUID value) {
    this.value = Objects.requireNonNull(value, "value");
  }

  public static OrderId of(UUID value) {
    return new OrderId(value);
  }

  public static OrderId newId() {
    return new OrderId(UUID.randomUUID());
  }

  public UUID value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrderId)) {
      return false;
    }
    OrderId orderId = (OrderId) o;
    return value.equals(orderId.value());
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
