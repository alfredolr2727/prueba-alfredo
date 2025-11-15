package com.jember.alfredo.prueba.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record WGS84Point(@Min(-90) @Max(90) Double latitude, @Min(-180) @Max(180) Double longitude) {}
