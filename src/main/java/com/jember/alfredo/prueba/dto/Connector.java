package com.jember.alfredo.prueba.dto;

import jakarta.validation.constraints.Min;

public record Connector(PlugType plugType, @Min(0) Integer powerLevel) {}
