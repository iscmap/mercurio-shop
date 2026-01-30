# Step 1.3 — API Contracts

## Headers
- `Idempotency-Key` (required for POST /orders) — string, max 100 chars
- `X-Correlation-Id` (optional) — if not provided, server generates one

## Status Codes
- 200 OK: successful GET, cancel if already cancelled
- 201 Created: order created
- 400 Bad Request: invalid payload/header
- 404 Not Found: resource not found (or ownership hidden later)
- 409 Conflict: idempotency key reused with different request body
- 422 Unprocessable Entity: business rule violation (e.g., cannot cancel confirmed order)

## Idempotency
POST /orders:
- If the same `Idempotency-Key` is sent again with the SAME request body → return the same response (same orderId)
- If the same `Idempotency-Key` is reused with a DIFFERENT body → return 409 Conflict
