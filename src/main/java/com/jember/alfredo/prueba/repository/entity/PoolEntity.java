package com.jember.alfredo.prueba.repository.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pool")
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
    
    // Constructors, getters y setters
    public PoolEntity() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDcsPoolId() { return dcsPoolId; }
    public void setDcsPoolId(String dcsPoolId) { this.dcsPoolId = dcsPoolId; }
    
    public String getIncomingPoolId() { return incomingPoolId; }
    public void setIncomingPoolId(String incomingPoolId) { this.incomingPoolId = incomingPoolId; }
    
    public Boolean getOpen24h() { return open24h; }
    public void setOpen24h(Boolean open24h) { this.open24h = open24h; }
    
    public String getAccess() { return access; }
    public void setAccess(String access) { this.access = access; }
    
    public String getPoolLocationType() { return poolLocationType; }
    public void setPoolLocationType(String poolLocationType) { this.poolLocationType = poolLocationType; }
    
    public List<PoolLocationEntity> getPoolLocations() { return poolLocations; }
    public void setPoolLocations(List<PoolLocationEntity> poolLocations) { this.poolLocations = poolLocations; }
    
    public List<ChargingStationEntity> getChargingStations() { return chargingStations; }
    public void setChargingStations(List<ChargingStationEntity> chargingStations) { this.chargingStations = chargingStations; }
}

