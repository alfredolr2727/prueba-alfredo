package com.jember.alfredo.prueba.mapper;

import com.jember.alfredo.prueba.dto.AccessType;
import com.jember.alfredo.prueba.dto.AuthenticationMethod;
import com.jember.alfredo.prueba.dto.ChargePointDetails;
import com.jember.alfredo.prueba.dto.ChargingStation;
import com.jember.alfredo.prueba.dto.Connector;
import com.jember.alfredo.prueba.dto.LocationType;
import com.jember.alfredo.prueba.dto.PlugType;
import com.jember.alfredo.prueba.dto.PoolLocation;
import com.jember.alfredo.prueba.dto.PoolLocationType;
import com.jember.alfredo.prueba.dto.PoolSearchResponse;
import com.jember.alfredo.prueba.dto.WGS84Point;
import com.jember.alfredo.prueba.repository.entity.ChargePointEntity;
import com.jember.alfredo.prueba.repository.entity.ChargingStationEntity;
import com.jember.alfredo.prueba.repository.entity.ConnectorEntity;
import com.jember.alfredo.prueba.repository.entity.PoolEntity;
import com.jember.alfredo.prueba.repository.entity.PoolLocationEntity;
import org.springframework.stereotype.Component;

@Component
public class PoolMapper {

  public PoolSearchResponse toDto(PoolEntity entity) {
    if (entity == null) return null;

    return new PoolSearchResponse(
        entity.getDcsPoolId(),
        entity.getIncomingPoolId(),
        entity.getOpen24h(),
        entity.getAccess() != null ? AccessType.valueOf(entity.getAccess()) : null,
        entity.getPoolLocationType() != null
            ? PoolLocationType.valueOf(entity.getPoolLocationType())
            : null,
        entity.getPoolLocations().stream().map(this::toLocationDto).toList(),
        entity.getChargingStations().stream()
            .map(this::toChargingStationDto)
            .toList());
  }

  private PoolLocation toLocationDto(PoolLocationEntity entity) {
    return new PoolLocation(
        entity.getCity(),
        entity.getCountryCode(),
        entity.getHouseNumber(),
        entity.getState(),
        entity.getStreet(),
        entity.getType() != null ? LocationType.valueOf(entity.getType()) : null,
        entity.getZipCode(),
        new WGS84Point(
            entity.getLatitude() != null ? entity.getLatitude().doubleValue() : null,
            entity.getLongitude() != null ? entity.getLongitude().doubleValue() : null));
  }

  private ChargingStation toChargingStationDto(ChargingStationEntity entity) {
    return new ChargingStation(
        entity.getDcsCsId(),
        entity.getIncomingCsId(),
        entity.getAuthMethods().stream()
            .map(am -> AuthenticationMethod.valueOf(am.getAuthMethod()))
            .toList(),
        entity.getChargePoints().stream().map(this::toChargePointDto).toList());
  }

  private ChargePointDetails toChargePointDto(ChargePointEntity entity) {
    return new ChargePointDetails(
        entity.getDcsCpId(),
        entity.getIncomingCpId(),
        entity.getDynamicInfoAvailable(),
        entity.getIsoNormedId(),
        entity.getConnectors().stream().map(this::toConnectorDto).toList());
  }

  private Connector toConnectorDto(ConnectorEntity entity) {
    return new Connector(
        entity.getPlugType() != null ? PlugType.valueOf(entity.getPlugType()) : null,
        entity.getPowerLevel());
  }
}
