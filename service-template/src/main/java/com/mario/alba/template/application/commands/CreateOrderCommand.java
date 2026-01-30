package com.mario.alba.template.application.commands;

import static com.mario.alba.common.util.Preconditions.requireNotBlank;

import com.mario.alba.template.domain.model.OrderLine;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class CreateOrderCommand {
  private final String idempotencyKey;
  private final List<OrderLine> lines;

  public CreateOrderCommand(String idempotencyKey, List<OrderLine> lines) {
    this.idempotencyKey = requireNotBlank(idempotencyKey, "idempotencyKey");
    this.lines = List.copyOf(Objects.requireNonNull(lines, "lines"));
    if (this.lines.isEmpty()) {
      throw new IllegalArgumentException("lines must not be empty");
    }
  }
}
