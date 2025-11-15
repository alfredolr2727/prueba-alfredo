package com.jember.alfredo.prueba.dto;

import java.util.List;

record ChargePointDetails(
    String dcsCpId,
    String incomingCpId,
    Boolean dynamicInfoAvailable,
    Boolean isonormedID,
    List<Connector> connectors) {}
