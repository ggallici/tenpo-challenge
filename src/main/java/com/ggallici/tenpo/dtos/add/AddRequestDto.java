package com.ggallici.tenpo.dtos.add;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// Si quiero snake_case en las request necesito @JsonProperty("first_operator")
public record AddRequestDto(@NotNull BigDecimal first, @NotNull BigDecimal second) { }
