package com.mario.alba.template.domain.model;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRecord {
  private String orderId;
  private String status;
  private List<OrderLine> lines;

  public OrderRecord(String orderId, String status, List<OrderLine> lines) {
    this.orderId = orderId;
    this.status = status;
    this.lines = List.copyOf(Objects.requireNonNull(lines, "lines"));
    ;
  }
}
