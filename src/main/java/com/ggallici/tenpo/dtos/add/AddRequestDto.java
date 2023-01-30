package com.ggallici.tenpo.dtos.add;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddRequestDto(@NotNull BigDecimal first, @NotNull BigDecimal second) { }
