package com.jember.alfredo.prueba.dto;

import java.util.List;

public record ChargingStation(
    String dcsCsId,
    String incomingCsId,
    List<AuthenticationMethod> chargingStationAuthMethods,
    List<ChargePointDetails> chargePoints) {}
