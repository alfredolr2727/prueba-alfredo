package com.jember.alfredo.prueba.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jember.alfredo.prueba.dto.*;
import com.jember.alfredo.prueba.service.ChargePointService;
import com.jember.alfredo.prueba.service.PoolService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DataController.class)
class DataControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private PoolService poolService;

  @MockitoBean private ChargePointService chargePointService;

  private static final String VALID_SUBSCRIPTION_KEY = "1234ABCD";
  private static final String INVALID_SUBSCRIPTION_KEY = "INVALID_KEY";

  @Test
  @DisplayName("POST /pool-find - Should return pool search results successfully")
  void testPoolSearchAndFilter_Success() throws Exception {
    // Arrange
    PoolSearchRequest request = new PoolSearchRequest(List.of("pool-001", "pool-002"), null);

    PoolSearchResponse response1 =
        new PoolSearchResponse(
            "pool-001", "incoming-pool-001", true, null, null, List.of(), List.of());

    PoolSearchResponse response2 =
        new PoolSearchResponse(
            "pool-002", "incoming-pool-002", false, null, null, List.of(), List.of());

    List<PoolSearchResponse> expectedResponses = List.of(response1, response2);

    when(poolService.searchPools(any(PoolSearchRequest.class))).thenReturn(expectedResponses);

    // Act & Assert
    mockMvc
        .perform(
            post("/pool-find")
                .header("Subscription-Key", VALID_SUBSCRIPTION_KEY)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].dcsPoolId").value("pool-001"))
        .andExpect(jsonPath("$[1].dcsPoolId").value("pool-002"));
  }

  @Test
  @DisplayName("POST /pool-find - Should return 403 when subscription key is invalid")
  void testPoolSearchAndFilter_InvalidSubscriptionKey() throws Exception {
    // Arrange
    PoolSearchRequest request = new PoolSearchRequest(List.of("pool-001"), null);

    // Act & Assert
    mockMvc
        .perform(
            post("/pool-find")
                .header("Subscription-Key", INVALID_SUBSCRIPTION_KEY)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("POST /pool-find - Should return 400 when request body is invalid")
  void testPoolSearchAndFilter_InvalidRequestBody() throws Exception {
    // Arrange - Empty list should fail validation (@NotEmpty)
    PoolSearchRequest request = new PoolSearchRequest(List.of(), null);

    // Act & Assert
    mockMvc
        .perform(
            post("/pool-find")
                .header("Subscription-Key", VALID_SUBSCRIPTION_KEY)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName(
      "POST /availability/charge-points/ - Should return charge point dynamic data successfully")
  void testGetDynamicPoiData_Success() throws Exception {
    // Arrange
    ChargePointDynStatusRequest request =
        new ChargePointDynStatusRequest(List.of("cp-001", "cp-002"));

    ChargePointDynStatusResponse status1 =
        new ChargePointDynStatusResponse(
            "cp-001", OperationalState.AVAILABLE, "2025-11-16T10:00:00Z");

    ChargePointDynStatusResponse status2 =
        new ChargePointDynStatusResponse(
            "cp-002", OperationalState.CHARGING, "2025-11-16T10:00:00Z");

    ChargePointDynStatusResponseStatus responseStatus =
        new ChargePointDynStatusResponseStatus(200, "Success", List.of());

    ChargePointDynStatusResponseList responseList =
        new ChargePointDynStatusResponseList(List.of(status1, status2), responseStatus);

    when(chargePointService.getChargePointsStatus(any())).thenReturn(responseList);

    // Act & Assert
    mockMvc
        .perform(
            post("/availability/charge-points/")
                .header("Subscription-Key", VALID_SUBSCRIPTION_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.chargePointDynStatusResponseList").exists())
        .andExpect(
            jsonPath("$.chargePointDynStatusResponseList.ChargePointDynStatusResponses").isArray())
        .andExpect(
            jsonPath("$.chargePointDynStatusResponseList.ChargePointDynStatusResponses.length()")
                .value(2))
        .andExpect(
            jsonPath(
                    "$.chargePointDynStatusResponseList.ChargePointDynStatusResponses[0].chargePointID")
                .value("cp-001"))
        .andExpect(
            jsonPath(
                    "$.chargePointDynStatusResponseList.ChargePointDynStatusResponses[1].chargePointID")
                .value("cp-002"));
  }

  @Test
  @DisplayName(
      "POST /availability/charge-points/ - Should return 403 when subscription key is invalid")
  void testGetDynamicPoiData_InvalidSubscriptionKey() throws Exception {
    // Arrange
    ChargePointDynStatusRequest request = new ChargePointDynStatusRequest(List.of("cp-001"));

    // Act & Assert
    mockMvc
        .perform(
            post("/availability/charge-points/")
                .header("Subscription-Key", INVALID_SUBSCRIPTION_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("POST /availability/charge-points/ - Should return 400 when request body is invalid")
  void testGetDynamicPoiData_NullChargePointIds() throws Exception {
    // Arrange - Null chargePointIds should fail validation (@NotNull)
    String invalidRequest = "{\"chargePointIds\": null}";

    // Act & Assert
    mockMvc
        .perform(
            post("/availability/charge-points/")
                .header("Subscription-Key", VALID_SUBSCRIPTION_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
        .andExpect(status().isBadRequest());
  }
}
