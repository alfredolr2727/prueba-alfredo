package com.jember.alfredo.prueba.repository.cache;

import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChargePointRepository {

  private final RedisTemplate<String, Object> redisTemplate;

  public Map<String, ChargePointDynStatusResponse> findByIds(List<String> ids) {
    List<String> keys = ids.stream().map(id -> "chargepoint:" + id).toList();

    List<Object> values = redisTemplate.opsForValue().multiGet(keys);

    return Objects.isNull(values)
        ? Map.of()
        : ids.stream()
            .filter(Objects::nonNull)
            .filter(id -> values.get(ids.indexOf(id)) != null)
            .collect(
                Collectors.toMap(
                    id -> id, id -> (ChargePointDynStatusResponse) values.get(ids.indexOf(id))));
  }
}
