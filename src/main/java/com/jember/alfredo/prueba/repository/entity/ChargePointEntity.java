package com.jember.alfredo.prueba.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "charge_point")
public class ChargePointEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "charging_station_id")
  private ChargingStationEntity chargingStation;

  @Column(name = "dcs_cp_id", unique = true, nullable = false)
  private String dcsCpId;

  @Column(name = "incoming_cp_id")
  private String incomingCpId;

  @Column(name = "dynamic_info_available")
  private Boolean dynamicInfoAvailable;

  @Column(name = "iso_normed_id")
  private Boolean isoNormedId;

  @OneToMany(mappedBy = "chargePoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ConnectorEntity> connectors = new ArrayList<>();

  // Constructors, getters y setters
  public ChargePointEntity() {}

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

  public String getDcsCpId() {
    return dcsCpId;
  }

  public void setDcsCpId(String dcsCpId) {
    this.dcsCpId = dcsCpId;
  }

  public String getIncomingCpId() {
    return incomingCpId;
  }

  public void setIncomingCpId(String incomingCpId) {
    this.incomingCpId = incomingCpId;
  }

  public Boolean getDynamicInfoAvailable() {
    return dynamicInfoAvailable;
  }

  public void setDynamicInfoAvailable(Boolean dynamicInfoAvailable) {
    this.dynamicInfoAvailable = dynamicInfoAvailable;
  }

  public Boolean getIsoNormedId() {
    return isoNormedId;
  }

  public void setIsoNormedId(Boolean isoNormedId) {
    this.isoNormedId = isoNormedId;
  }

  public List<ConnectorEntity> getConnectors() {
    return connectors;
  }

  public void setConnectors(List<ConnectorEntity> connectors) {
    this.connectors = connectors;
  }
}
