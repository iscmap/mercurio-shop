package com.mario.alba.common.events;

import static com.mario.alba.common.util.Preconditions.requireNotBlank;

import com.mario.alba.common.value.LineItem;
import java.util.List;
import java.util.Objects;

public class OrderCreatedEvent {
  private final String eventId;
  private final String occurredAt;
  private final String correlationId;
  private final int schemaVersion;

  private final String orderId;
  private final String customerId;
  private final List<LineItem> items;

  public OrderCreatedEvent(
      String eventId,
      String occurredAt,
      String correlationId,
      int schemaVersion,
      String orderId,
      String customerId,
      List<LineItem> items) {
    this.eventId = requireNotBlank(eventId, "eventId");
    this.occurredAt = requireNotBlank(occurredAt, "occurredAt");
    this.correlationId = requireNotBlank(correlationId, "correlationId");
    this.schemaVersion = schemaVersion;

    this.orderId = requireNotBlank(orderId, "orderId");
    this.customerId = requireNotBlank(customerId, "customerId");
    this.items = List.copyOf(Objects.requireNonNull(items, "items"));
  }

  public String eventId() {
    return eventId;
  }

  public String occurredAt() {
    return occurredAt;
  }

  public String correlationId() {
    return correlationId;
  }

  public int schemaVersion() {
    return schemaVersion;
  }

  public String orderId() {
    return orderId;
  }

  public String customerId() {
    return customerId;
  }

  public List<LineItem> items() {
    return items;
  }
}
