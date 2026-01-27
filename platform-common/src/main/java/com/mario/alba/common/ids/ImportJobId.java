package com.mario.alba.common.ids;

import java.util.Objects;
import java.util.UUID;

public final class ImportJobId {
  private final UUID value;

  private ImportJobId(UUID value) {
    this.value = Objects.requireNonNull(value, "value");
  }

  public static ImportJobId of(UUID value) {
    return new ImportJobId(value);
  }

  public static ImportJobId newId() {
    return new ImportJobId(UUID.randomUUID());
  }

  public UUID value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ImportJobId)) return false;
    ImportJobId that = (ImportJobId) o;
    return value.equals(that.value);
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
