package com.jember.alfredo.prueba.dto;

import jakarta.validation.constraints.NotNull;

record ChargePointDynStatusResponse(
    @NotNull String chargePointID,
    @NotNull OperationalState OperationalState,
    @NotNull String timestamp) {}
