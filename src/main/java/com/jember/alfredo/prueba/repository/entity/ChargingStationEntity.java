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
import lombok.Data;

@Entity
@Table(name = "charging_station")
@Data
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
}
