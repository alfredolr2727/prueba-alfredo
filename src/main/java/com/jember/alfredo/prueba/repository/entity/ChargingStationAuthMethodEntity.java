package com.jember.alfredo.prueba.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "charging_station_auth_method")
public class ChargingStationAuthMethodEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "charging_station_id")
  private ChargingStationEntity chargingStation;

  @Column(name = "auth_method", length = 50)
  private String authMethod;

  // Constructors, getters y setters
  public ChargingStationAuthMethodEntity() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ChargingStationEntity getChargingStation() {
    return chargingStation;
  }

  public void setChargingStation(ChargingStationEntity chargingStation) {
    this.chargingStation = chargingStation;
  }

  public String getAuthMethod() {
    return authMethod;
  }

  public void setAuthMethod(String authMethod) {
    this.authMethod = authMethod;
  }
}
