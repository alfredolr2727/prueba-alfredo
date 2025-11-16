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
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Table(name = "pool_location")
@Data
public class PoolLocationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pool_id")
  private PoolEntity pool;

  private String city;

  @Column(name = "country_code", length = 2)
  private String countryCode;

  @Column(name = "house_number", length = 50)
  private String houseNumber;

  private String state;
  private String street;

  @Column(length = 50)
  private String type;

  @Column(name = "zip_code", length = 20)
  private String zipCode;

  @Column(precision = 10, scale = 7)
  private BigDecimal latitude;

  @Column(precision = 10, scale = 7)
  private BigDecimal longitude;
}
