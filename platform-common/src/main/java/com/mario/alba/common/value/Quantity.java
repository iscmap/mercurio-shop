package com.mario.alba.common.value;

public final class Quantity {
  private final int value;

  private Quantity(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Quantity must be > 0");
    }
    this.value = value;
  }

  public static Quantity of(int value) {
    return new Quantity(value);
  }

  public int value() {
    return value;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Quantity)) return false;
    Quantity quantity = (Quantity) o;
    return value == quantity.value;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(value);
  }
}
