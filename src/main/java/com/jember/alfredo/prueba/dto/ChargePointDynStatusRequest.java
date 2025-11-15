package com.jember.alfredo.prueba.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ChargePointDynStatusRequest(@NotNull List<String> chargePointIds) {}
