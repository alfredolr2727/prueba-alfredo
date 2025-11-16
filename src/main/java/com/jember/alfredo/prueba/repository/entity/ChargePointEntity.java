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
@Table(name = "charge_point")
@Data
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
}
