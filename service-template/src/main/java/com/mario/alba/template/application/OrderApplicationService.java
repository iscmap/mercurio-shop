package com.mario.alba.template.application;

import com.mario.alba.template.application.commands.CreateOrderCommand;
import com.mario.alba.template.domain.model.IdempotencyRecord;
import com.mario.alba.template.domain.model.OrderLine;
import com.mario.alba.template.domain.model.OrderRecord;
import com.mario.alba.template.domain.ports.IdempotencyStorePort;
import com.mario.alba.template.domain.ports.OrderRepositoryPort;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderApplicationService {

  private final OrderRepositoryPort orders;
  private final IdempotencyStorePort idempotency;

  public OrderApplicationService(OrderRepositoryPort orders, IdempotencyStorePort idempotency) {
    this.orders = orders;
    this.idempotency = idempotency;
  }

  public CreateOrderResult createOrder(CreateOrderCommand command) {
    String key = command.getIdempotencyKey();
    String requestHash = sha256Hex(canonicalize(command.getLines()));

    Optional<IdempotencyRecord> existing = idempotency.findByKey(key);
    if (existing.isPresent()) {
      IdempotencyRecord record = existing.get();
      if (!record.getRequestHash().equals(requestHash)) {
        return CreateOrderResult.conflict("Idempotency-Key reused with different request body");
      }
      return CreateOrderResult.ok(record.getOrderId(), record.getStatus(), true);
    }

    String orderId = UUID.randomUUID().toString();
    OrderRecord order = new OrderRecord(orderId, "PENDING", command.getLines());
    orders.save(order);

    idempotency.save(new IdempotencyRecord(key, requestHash, orderId, order.getStatus()));
    return CreateOrderResult.ok(orderId, order.getStatus(), false);
  }

  public Optional<OrderRecord> getOrder(String orderId) {
    return orders.findById(orderId);
  }

  public List<OrderRecord> listOrders() {
    return orders.findAll();
  }

  public CancelResult cancelOrder(String orderId) {
    Optional<OrderRecord> existing = orders.findById(orderId);
    if (!existing.isPresent()) {
      return CancelResult.notFound();
    }

    OrderRecord order = existing.get();
    if (!"PENDING".equals(order.getStatus())) {
      return CancelResult.notAllowed("Only PENDING orders can be cancelled");
    }

    order.setStatus("CANCELLED");
    orders.save(order);
    return CancelResult.ok(orderId, order.getStatus());
  }

  private static String canonicalize(List<OrderLine> lines) {
    StringBuilder sb = new StringBuilder();
    for (OrderLine line : lines) {
      sb.append(line.getSku().value())
          .append("|")
          .append(line.getQuantity().value())
          .append("|")
          .append(line.getUnitPrice().amountMinor())
          .append("|")
          .append(line.getUnitPrice().currency())
          .append(";");
    }
    return sb.toString();
  }

  private static String sha256Hex(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashed = md.digest(input.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : hashed) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (Exception e) {
      throw new IllegalStateException("Unable to hash request", e);
    }
  }
}
