package com.jember.alfredo.prueba.dto;

public record PoolLocation(
    String city,
    String countryCode,
    String houseNumber,
    String state,
    String street,
    LocationType type,
    String zipCode,
    WGS84Point coordinates) {}
