package com.mario.alba.common.value;

import java.util.Objects;

public final class Money {
  private final long amountMinor;
  private final String currency; // e.g., "MXN", "USD"

  private Money(long amountMinor, String currency) {
    if (amountMinor < 0) {
      throw new IllegalArgumentException("Money amount must be >= 0");
    }
    this.currency = normalizeCurrency(currency);
    this.amountMinor = amountMinor;
  }

  public static Money ofMinor(long amountMinor, String currency) {
    return new Money(amountMinor, currency);
  }

  public long amountMinor() {
    return amountMinor;
  }

  public String currency() {
    return currency;
  }

  public Money plus(Money other) {
    requireSameCurrency(other);
    return new Money(this.amountMinor + other.amountMinor, this.currency);
  }

  public Money times(int multiplier) {
    if (multiplier < 0) {
      throw new IllegalArgumentException("Multiplier must be >= 0");
    }
    return new Money(this.amountMinor * multiplier, this.currency);
  }

  private void requireSameCurrency(Money other) {
    Objects.requireNonNull(other, "other");
    if (!this.currency.equals(other.currency)) {
      throw new IllegalArgumentException(
          "Currency mismatch: " + this.currency + " vs " + other.currency);
    }
  }

  private static String normalizeCurrency(String raw) {
    Objects.requireNonNull(raw, "currency");
    String c = raw.trim().toUpperCase();
    if (c.length() != 3) {
      throw new IllegalArgumentException("Currency must be ISO-4217 format (3 letters)");
    }
    return c;
  }

  @Override
  public String toString() {
    return amountMinor + " " + currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Money)) return false;
    Money money = (Money) o;
    return amountMinor == money.amountMinor && currency.equals(money.currency);
  }

  @Override
  public int hashCode() {
    int result = Long.hashCode(amountMinor);
    result = 31 * result + currency.hashCode();
    return result;
  }
}
