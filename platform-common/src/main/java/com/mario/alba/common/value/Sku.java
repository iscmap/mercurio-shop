package com.mario.alba.common.value;

import java.util.Objects;

public class Sku {

  private final String value;

  private Sku(String value) {
    this.value = normalize(value);
    if (this.value.isEmpty()) {
      throw new IllegalArgumentException("SKU must not be blank");
    }
    if (this.value.length() > 64) {
      throw new IllegalArgumentException("SKU must be <= 64 chars");
    }
  }

  public static Sku of(String value) {
    return new Sku(value);
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Sku)) return false;
    Sku sku = (Sku) o;
    return value.equals(sku.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  private static String normalize(String raw) {
    Objects.requireNonNull(raw, "value");
    return raw.trim().toUpperCase();
  }
}
