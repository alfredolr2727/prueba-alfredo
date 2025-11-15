package com.jember.alfredo.prueba.service;

import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponse;
import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponseList;
import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponseStatus;
import com.jember.alfredo.prueba.repository.cache.ChargePointRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChargePointService {

  private final ChargePointRepository chargePointRepository;

  public ChargePointDynStatusResponseList getChargePointsStatus(List<String> requestedIds) {
    // Obtener todos los charge points en una sola llamada a Redis
    Map<String, ChargePointDynStatusResponse> foundChargePoints =
        chargePointRepository.findByIds(requestedIds);

    // Separar encontrados e inválidos en una sola pasada
    List<ChargePointDynStatusResponse> validChargePoints = new ArrayList<>();
    List<String> invalidIds = new ArrayList<>();

    for (String id : requestedIds) {
      if (foundChargePoints.containsKey(id)) {
        validChargePoints.add(foundChargePoints.get(id));
      } else {
        invalidIds.add(id);
      }
    }

    ChargePointDynStatusResponseStatus chargePointStatus;
    if (invalidIds.isEmpty()) {
      chargePointStatus =
          new ChargePointDynStatusResponseStatus(200, "Success", Collections.emptyList());
    } else {
      chargePointStatus =
          new ChargePointDynStatusResponseStatus(
              1000,
              "InvalidRequest - Some of the requested ChargePoint-Ids are not valid",
              invalidIds);
    }

    return new ChargePointDynStatusResponseList(validChargePoints, chargePointStatus);
  }
}
