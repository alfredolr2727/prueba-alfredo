package com.jember.alfredo.prueba.dto;

import java.util.List;

public record PoolSearchResponse(
    String dcsPoolId,
    String incomingPoolId,
    Boolean open24h,
    AccessType access,
    PoolLocationType poolLocationType,
    List<PoolLocation> poolLocations,
    List<ChargingStation> chargingStations) {}
