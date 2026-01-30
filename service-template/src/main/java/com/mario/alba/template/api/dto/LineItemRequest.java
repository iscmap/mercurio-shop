package com.mario.alba.template.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LineItemRequest {

  @NotNull private String sku;

  @NotNull private Integer quantity;

  @NotNull private Long unitPriceMinor;

  @NotNull private String currency;
}
