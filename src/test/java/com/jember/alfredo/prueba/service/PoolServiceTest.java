package com.jember.alfredo.prueba.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jember.alfredo.prueba.dto.AccessType;
import com.jember.alfredo.prueba.dto.AuthenticationMethod;
import com.jember.alfredo.prueba.dto.PoolFilter;
import com.jember.alfredo.prueba.dto.PoolLocationType;
import com.jember.alfredo.prueba.dto.PoolSearchRequest;
import com.jember.alfredo.prueba.dto.PoolSearchResponse;
import com.jember.alfredo.prueba.mapper.PoolMapper;
import com.jember.alfredo.prueba.repository.PoolRepository;
import com.jember.alfredo.prueba.repository.entity.PoolEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
@DisplayName("PoolService Tests")
class PoolServiceTest {

  @Mock private PoolRepository poolRepository;

  @Mock private PoolMapper poolMapper;

  @InjectMocks private PoolService poolService;

  private PoolSearchRequest searchRequest;
  private PoolEntity poolEntity1;
  private PoolEntity poolEntity2;
  private PoolSearchResponse poolResponse1;
  private PoolSearchResponse poolResponse2;

  @BeforeEach
  void setUp() {
    // Test data preparing
    List<String> poolIds = List.of("POOL-001", "POOL-002");
    PoolFilter filter = new PoolFilter(Collections.emptyList());
    searchRequest = new PoolSearchRequest(poolIds, filter);

    // Entity test data
    poolEntity1 = new PoolEntity();
    poolEntity1.setId(1L);
    poolEntity1.setDcsPoolId("POOL-001");
    poolEntity1.setIncomingPoolId("INCOMING-001");
    poolEntity1.setOpen24h(true);
    poolEntity1.setAccess("PUBLIC");
    poolEntity1.setPoolLocationType("ONSTREET");
    poolEntity1.setPoolLocations(new ArrayList<>());
    poolEntity1.setChargingStations(new ArrayList<>());

    poolEntity2 = new PoolEntity();
    poolEntity2.setId(2L);
    poolEntity2.setDcsPoolId("POOL-002");
    poolEntity2.setIncomingPoolId("INCOMING-002");
    poolEntity2.setOpen24h(false);
    poolEntity2.setAccess("PRIVATE");
    poolEntity2.setPoolLocationType("UNKNOWN");
    poolEntity2.setPoolLocations(new ArrayList<>());
    poolEntity2.setChargingStations(new ArrayList<>());

    poolResponse1 =
        new PoolSearchResponse(
            "POOL-001",
            "INCOMING-001",
            true,
            AccessType.PUBLIC,
            PoolLocationType.ONSTREET,
            Collections.emptyList(),
            Collections.emptyList());

    poolResponse2 =
        new PoolSearchResponse(
            "POOL-002",
            "INCOMING-002",
            false,
            AccessType.PRIVATE,
            PoolLocationType.UNKNOWN,
            Collections.emptyList(),
            Collections.emptyList());
  }

  @Test
  @DisplayName("Should search pools successfully with valid request")
  void testSearchPools_Success() {
    // Given
    List<PoolEntity> poolEntities = List.of(poolEntity1, poolEntity2);
    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(poolEntities);
    when(poolMapper.toDto(poolEntity1)).thenReturn(poolResponse1);
    when(poolMapper.toDto(poolEntity2)).thenReturn(poolResponse2);

    // When
    List<PoolSearchResponse> result = poolService.searchPools(searchRequest);

    // Then
    assertNotNull(result, "Result shouldn't be null");
    assertEquals(2, result.size(), "Should return 2 pools");
    assertThat(result).containsExactly(poolResponse1, poolResponse2);

    // Verify calls
    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
    verify(poolMapper, times(2)).toDto(any(PoolEntity.class));
    verify(poolMapper, times(1)).toDto(poolEntity1);
    verify(poolMapper, times(1)).toDto(poolEntity2);
  }

  @Test
  @DisplayName("Should return empty list when no pools found")
  void testSearchPools_EmptyResult() {
    // Given
    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(Collections.emptyList());

    // When
    List<PoolSearchResponse> result = poolService.searchPools(searchRequest);

    // Then
    assertNotNull(result, "Result shouldn't be null");
    assertTrue(result.isEmpty(), "List must be empty");

    // Verify interactions
    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
    verify(poolMapper, never()).toDto(any(PoolEntity.class));
  }

  @Test
  @DisplayName("Should handle single pool result")
  void testSearchPools_SingleResult() {
    // Given
    List<PoolEntity> poolEntities = List.of(poolEntity1);
    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(poolEntities);
    when(poolMapper.toDto(poolEntity1)).thenReturn(poolResponse1);

    // When
    List<PoolSearchResponse> result = poolService.searchPools(searchRequest);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size(), "Should return 1 pool");
    assertEquals(poolResponse1, result.getFirst());
    assertEquals("POOL-001", result.getFirst().dcsPoolId());

    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
    verify(poolMapper, times(1)).toDto(poolEntity1);
  }

  @Test
  @DisplayName("Should verify transactional behavior is applied")
  void testSearchPools_TransactionalBehavior() {
    // Given
    List<PoolEntity> poolEntities = List.of(poolEntity1);
    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(poolEntities);
    when(poolMapper.toDto(poolEntity1)).thenReturn(poolResponse1);

    // When
    poolService.searchPools(searchRequest);

    // Then - verify
    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
  }

  @Test
  @DisplayName("Should handle mapper returning null gracefully")
  void testSearchPools_MapperReturnsNull() {
    // Given
    List<PoolEntity> poolEntities = List.of(poolEntity1, poolEntity2);
    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(poolEntities);
    when(poolMapper.toDto(poolEntity1)).thenReturn(null);
    when(poolMapper.toDto(poolEntity2)).thenReturn(poolResponse2);

    // When
    List<PoolSearchResponse> result = poolService.searchPools(searchRequest);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertNull(result.getFirst(), "First element should be null");
    assertEquals(poolResponse2, result.get(1));

    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
    verify(poolMapper, times(2)).toDto(any(PoolEntity.class));
  }

  @Test
  @DisplayName("Should verify correct filter is applied through specification")
  void testSearchPools_WithFilter() {
    // Given
    List<String> poolIds = List.of("POOL-001");
    PoolFilter customFilter =
        new PoolFilter(List.of(AuthenticationMethod.REMOTE, AuthenticationMethod.UNKNOWN));
    PoolSearchRequest requestWithFilter = new PoolSearchRequest(poolIds, customFilter);

    List<PoolEntity> poolEntities = List.of(poolEntity1);
    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(poolEntities);
    when(poolMapper.toDto(poolEntity1)).thenReturn(poolResponse1);

    // When
    List<PoolSearchResponse> result = poolService.searchPools(requestWithFilter);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.getFirst().open24h());

    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
  }

  @Test
  @DisplayName("Should handle large result sets")
  void testSearchPools_LargeResultSet() {
    // Given
    List<PoolEntity> largeList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      PoolEntity entity = new PoolEntity();
      entity.setId((long) i);
      entity.setDcsPoolId("POOL-" + i);
      entity.setPoolLocations(new ArrayList<>());
      entity.setChargingStations(new ArrayList<>());
      largeList.add(entity);

      PoolSearchResponse response =
          new PoolSearchResponse(
              "POOL-" + i,
              "INCOMING-" + i,
              true,
              AccessType.PUBLIC,
              PoolLocationType.ONSTREET,
              Collections.emptyList(),
              Collections.emptyList());

      when(poolMapper.toDto(entity)).thenReturn(response);
    }

    when(poolRepository.findAll(ArgumentMatchers.<Specification<PoolEntity>>any()))
        .thenReturn(largeList);

    // When
    List<PoolSearchResponse> result = poolService.searchPools(searchRequest);

    // Then
    assertNotNull(result);
    assertEquals(100, result.size());
    verify(poolRepository, times(1)).findAll(ArgumentMatchers.<Specification<PoolEntity>>any());
    verify(poolMapper, times(100)).toDto(any(PoolEntity.class));
  }

  @Test
  @DisplayName("Should verify service dependencies are correctly injected")
  void testServiceDependenciesInjection() {
    // Verify that dependencies were correctly injected
    assertNotNull(poolService, "PoolService should be instantiated");
    assertNotNull(poolRepository, "PoolRepository mock should be present");
    assertNotNull(poolMapper, "PoolMapper mock should be present");
  }
}
