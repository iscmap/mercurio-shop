package com.mario.alba.template.api.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {
  private final String correlationId;
  private final String code;
  private final String message;

  public ErrorResponse(String correlationId, String code, String message) {
    this.correlationId = correlationId;
    this.code = code;
    this.message = message;
  }
}
