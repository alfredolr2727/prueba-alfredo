package com.jember.alfredo.prueba.controller;

import com.jember.alfredo.prueba.dto.ChargePointDynStatusRequest;
import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponseList;
import com.jember.alfredo.prueba.dto.PoolSearchRequest;
import com.jember.alfredo.prueba.dto.PoolSearchResponse;
import com.jember.alfredo.prueba.service.PoolService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** POI Data API Controller Provides information about e-charging points */
@RestController
@RequestMapping
@Validated
public class PruebaController {

  private final PoolService poolService;

  public PruebaController(PoolService poolService) {
    this.poolService = poolService;
  }

  /**
   * POST /pool-find Returns a response object that contains a list of pools.
   *
   * @param subscriptionKey Subscription key for authentication
   * @param request Pool search request with IDs and optional filters
   * @return List of pool search responses
   */
  @PostMapping(value = "/pool-find", consumes = "application/json", produces = "application/json")
  public ResponseEntity<List<PoolSearchResponse>> poolSearchAndFilter(
      @NotNull @RequestHeader("Subscription-Key") String subscriptionKey,
      @Valid @RequestBody PoolSearchRequest request) {

    List<PoolSearchResponse> results = poolService.searchPools(request);
    return ResponseEntity.ok(results);
  }

  /**
   * POST /availability/charge-points/ Delivers dynamic data for a set of charge points
   *
   * @param subscriptionKey Subscription key for authentication
   * @param request List of charge point IDs to get dynamic data for
   * @return Dynamic status data for requested charge points
   */
  @PostMapping(
      value = "/availability/charge-points/",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ChargePointDynStatusResponseList> getDynamicPoiData(
      @RequestHeader("Subscription-Key") String subscriptionKey,
      @Valid @RequestBody ChargePointDynStatusRequest request) {

    // TODO
    throw new UnsupportedOperationException("Pending implementation");
  }
}
