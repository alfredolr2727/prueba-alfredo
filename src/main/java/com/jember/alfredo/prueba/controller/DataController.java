package com.jember.alfredo.prueba.controller;

import com.jember.alfredo.prueba.controller.exception.ForbiddenException;
import com.jember.alfredo.prueba.dto.ChargePointDynStatusRequest;
import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponseList;
import com.jember.alfredo.prueba.dto.PoolSearchRequest;
import com.jember.alfredo.prueba.dto.PoolSearchResponse;
import com.jember.alfredo.prueba.dto.ResponseWrapper;
import com.jember.alfredo.prueba.service.ChargePointService;
import com.jember.alfredo.prueba.service.PoolService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** POI Data API Controller Provides information about e-charging points */
@RestController
@RequestMapping
@Validated
@RequiredArgsConstructor
public class DataController {

  private final PoolService poolService;

  private final ChargePointService chargePointService;

  private static final String FAKE_VALID_KEY = "1234ABCD";

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
      @RequestHeader(value = "Content-Type") String contentType,
      @Valid @RequestBody PoolSearchRequest request) {

    // Fake Validation
    validateSubscriptionKey(subscriptionKey);

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
  public ResponseEntity<ResponseWrapper> getDynamicPoiData(
      @RequestHeader("Subscription-Key") String subscriptionKey,
      @Valid @RequestBody ChargePointDynStatusRequest request) {

    // Fake Validation
    validateSubscriptionKey(subscriptionKey);

    ChargePointDynStatusResponseList responseList =
        chargePointService.getChargePointsStatus(request.chargePointIds());

    return ResponseEntity.ok(new ResponseWrapper(responseList));
  }

  private void validateSubscriptionKey(String subscriptionKey) {
    if (!subscriptionKey.equals(FAKE_VALID_KEY)) {
      throw new ForbiddenException("You are not allowed to use this endpoint!");
    }
  }
}
