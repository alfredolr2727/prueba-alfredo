package com.jember.alfredo.prueba.repository.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "pool")
@Data
public class PoolEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "dcs_pool_id", unique = true, nullable = false)
  private String dcsPoolId;

  @Column(name = "incoming_pool_id")
  private String incomingPoolId;

  @Column(name = "open_24h")
  private Boolean open24h;

  @Column(name = "access", length = 50)
  private String access;

  @Column(name = "pool_location_type", length = 50)
  private String poolLocationType;

  @OneToMany(mappedBy = "pool", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PoolLocationEntity> poolLocations = new ArrayList<>();

  @OneToMany(mappedBy = "pool", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ChargingStationEntity> chargingStations = new ArrayList<>();
}
