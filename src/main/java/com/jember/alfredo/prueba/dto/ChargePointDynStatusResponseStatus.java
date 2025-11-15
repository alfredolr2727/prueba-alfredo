package com.jember.alfredo.prueba.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ChargePointDynStatusResponseStatus(
    @NotNull Integer status, @NotNull String description, List<String> invalidChargePointIDs) {}
