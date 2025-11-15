package com.jember.alfredo.prueba.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseWrapper(
    @JsonProperty("ChargePointDynStatusResponseList")
        ChargePointDynStatusResponseList chargePointDynStatusResponseList) {}
