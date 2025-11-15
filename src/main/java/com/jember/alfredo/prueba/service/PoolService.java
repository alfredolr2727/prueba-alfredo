package com.jember.alfredo.prueba.service;

import com.jember.alfredo.prueba.dto.PoolSearchRequest;
import com.jember.alfredo.prueba.dto.PoolSearchResponse;
import com.jember.alfredo.prueba.mapper.PoolMapper;
import com.jember.alfredo.prueba.repository.PoolRepository;
import com.jember.alfredo.prueba.repository.entity.PoolEntity;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PoolService {

  private final PoolRepository poolRepository;
  private final PoolMapper poolMapper;

  public PoolService(PoolRepository poolRepository, PoolMapper poolMapper) {
    this.poolRepository = poolRepository;
    this.poolMapper = poolMapper;
  }

  @Transactional(readOnly = true)
  public List<PoolSearchResponse> searchPools(PoolSearchRequest request) {
    List<PoolEntity> poolEntities;

    if (!request.dcsPoolIds().isEmpty()) {
      poolEntities = poolRepository.findByDcsPoolIdIn(request.dcsPoolIds());
    } else {
      poolEntities = poolRepository.findAll();
    }

    return poolEntities.stream().map(poolMapper::toDto).toList();
  }
}
