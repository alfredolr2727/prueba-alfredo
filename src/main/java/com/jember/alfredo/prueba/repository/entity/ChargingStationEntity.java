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
@Table(name = "charging_station")
public class ChargingStationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pool_id")
  private PoolEntity pool;

  @Column(name = "dcs_cs_id", unique = true, nullable = false)
  private String dcsCsId;

  @Column(name = "incoming_cs_id")
  private String incomingCsId;

  @OneToMany(mappedBy = "chargingStation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ChargingStationAuthMethodEntity> authMethods = new ArrayList<>();

  @OneToMany(mappedBy = "chargingStation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ChargePointEntity> chargePoints = new ArrayList<>();

  // Constructors, getters y setters
  public ChargingStationEntity() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PoolEntity getPool() {
    return pool;
  }

  public void setPool(PoolEntity pool) {
    this.pool = pool;
  }

  public String getDcsCsId() {
    return dcsCsId;
  }

  public void setDcsCsId(String dcsCsId) {
    this.dcsCsId = dcsCsId;
  }

  public String getIncomingCsId() {
    return incomingCsId;
  }

  public void setIncomingCsId(String incomingCsId) {
    this.incomingCsId = incomingCsId;
  }

  public List<ChargingStationAuthMethodEntity> getAuthMethods() {
    return authMethods;
  }

  public void setAuthMethods(List<ChargingStationAuthMethodEntity> authMethods) {
    this.authMethods = authMethods;
  }

  public List<ChargePointEntity> getChargePoints() {
    return chargePoints;
  }

  public void setChargePoints(List<ChargePointEntity> chargePoints) {
    this.chargePoints = chargePoints;
  }
}
