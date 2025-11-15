package com.jember.alfredo.prueba.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PoolSearchRequest(@NotNull @NotEmpty List<String> dcsPoolIds, PoolFilter filterCriteria) {}
