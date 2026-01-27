package com.mario.alba.common.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MoneyTest {

  @Test
  void plus_requires_same_currency() {
    Money a = Money.ofMinor(100, "MXN");
    Money b = Money.ofMinor(100, "USD");

    assertThrows(IllegalArgumentException.class, () -> a.plus(b));
  }

  @Test
  void times_multiplies_amount() {
    Money a = Money.ofMinor(100, "MXN");
    Money b = a.times(3);

    assertEquals(300, b.amountMinor());
    assertEquals("MXN", b.currency());
  }
}
