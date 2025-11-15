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
@Table(name = "connector")
public class ConnectorEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "charge_point_id")
  private ChargePointEntity chargePoint;

  @Column(name = "plug_type", length = 50)
  private String plugType;

  @Column(name = "power_level")
  private Integer powerLevel;

  // Constructors, getters y setters
  public ConnectorEntity() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ChargePointEntity getChargePoint() {
    return chargePoint;
  }

  public void setChargePoint(ChargePointEntity chargePoint) {
    this.chargePoint = chargePoint;
  }

  public String getPlugType() {
    return plugType;
  }

  public void setPlugType(String plugType) {
    this.plugType = plugType;
  }

  public Integer getPowerLevel() {
    return powerLevel;
  }

  public void setPowerLevel(Integer powerLevel) {
    this.powerLevel = powerLevel;
  }
}
