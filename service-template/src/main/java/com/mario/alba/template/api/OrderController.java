package com.mario.alba.template.api;

import com.mario.alba.common.value.Money;
import com.mario.alba.common.value.Quantity;
import com.mario.alba.common.value.Sku;
import com.mario.alba.template.api.dto.*;
import com.mario.alba.template.application.CancelResult;
import com.mario.alba.template.application.CreateOrderResult;
import com.mario.alba.template.application.OrderApplicationService;
import com.mario.alba.template.application.commands.CreateOrderCommand;
import com.mario.alba.template.domain.model.OrderLine;
import com.mario.alba.template.domain.model.OrderRecord;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderApplicationService service;

  public OrderController(OrderApplicationService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<?> create(
      @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
      @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
      @Valid @RequestBody CreateOrderRequest request) {
    String corr = correlationId != null ? correlationId : UUID.randomUUID().toString();

    if (idempotencyKey == null || idempotencyKey.trim().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ErrorResponse(corr, "BAD_REQUEST", "Missing Idempotency-Key header"));
    }
    if (idempotencyKey.length() > 100) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ErrorResponse(corr, "BAD_REQUEST", "Idempotency-Key must be <= 100 chars"));
    }

    List<OrderLine> lines =
        request.getItems().stream()
            .map(
                i ->
                    new OrderLine(
                        Sku.of(i.getSku()),
                        Quantity.of(i.getQuantity()),
                        Money.ofMinor(i.getUnitPriceMinor(), i.getCurrency())))
            .collect(Collectors.toList());

    CreateOrderCommand command = new CreateOrderCommand(idempotencyKey.trim(), lines);

    CreateOrderResult result = service.createOrder(command);
    if (result.isConflict()) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .header("X-Correlation-Id", corr)
          .body(new ErrorResponse(corr, "IDEMPOTENCY_CONFLICT", result.getMessage()));
    }

    HttpStatus status = result.isDuplicate() ? HttpStatus.OK : HttpStatus.CREATED;
    return ResponseEntity.status(status)
        .header("X-Correlation-Id", corr)
        .body(new CreateOrderResponse(result.getOrderId(), result.getStatus()));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<?> getById(
      @PathVariable("orderId") String orderId,
      @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
    String corr = correlationId != null ? correlationId : UUID.randomUUID().toString();

    Optional<OrderRecord> found = service.getOrder(orderId);
    if (!found.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .header("X-Correlation-Id", corr)
          .body(new ErrorResponse(corr, "NOT_FOUND", "Order not found"));
    }

    return ResponseEntity.ok().header("X-Correlation-Id", corr).body(toResponse(found.get()));
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> list() {
    List<OrderResponse> response =
        service.listOrders().stream().map(this::toResponse).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<?> cancel(
      @PathVariable("orderId") String orderId,
      @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
    String corr = correlationId != null ? correlationId : UUID.randomUUID().toString();

    CancelResult result = service.cancelOrder(orderId);
    if (result.isNotFound()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .header("X-Correlation-Id", corr)
          .body(new ErrorResponse(corr, "NOT_FOUND", result.getMessage()));
    }
    if (result.isNotAllowed()) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
          .header("X-Correlation-Id", corr)
          .body(new ErrorResponse(corr, "NOT_ALLOWED", result.getMessage()));
    }

    return ResponseEntity.ok()
        .header("X-Correlation-Id", corr)
        .body(new CreateOrderResponse(result.getOrderId(), result.getStatus()));
  }

  private OrderResponse toResponse(OrderRecord order) {
    List<OrderLineSummary> items =
        order.getLines().stream()
            .map(l -> new OrderLineSummary(l.getSku().value(), l.getQuantity().value()))
            .collect(Collectors.toList());

    return new OrderResponse(order.getOrderId(), order.getStatus(), items);
  }
}
