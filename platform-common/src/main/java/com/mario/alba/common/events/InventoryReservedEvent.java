package com.mario.alba.common.events;

import static com.mario.alba.common.util.Preconditions.requireNotBlank;

public class InventoryReservedEvent {

  private final String eventId;
  private final String occurredAt;
  private final String correlationId;
  private final int schemaVersion;

  private final String orderId;

  public InventoryReservedEvent(
      String eventId, String occurredAt, String correlationId, int schemaVersion, String orderId) {
    this.eventId = requireNotBlank(eventId, "eventId");
    this.occurredAt = requireNotBlank(occurredAt, "occurredAt");
    this.correlationId = requireNotBlank(correlationId, "correlationId");
    this.schemaVersion = schemaVersion;
    this.orderId = requireNotBlank(orderId, "orderId");
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
}
