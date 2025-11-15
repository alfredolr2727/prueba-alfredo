package com.jember.alfredo.prueba.cache.config;

import com.jember.alfredo.prueba.dto.ChargePointDynStatusResponse;
import com.jember.alfredo.prueba.dto.OperationalState;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

  private final RedisTemplate<String, Object> redisTemplate;

  public DataLoader(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void run(String... args) {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    List<ChargePointDynStatusResponse> chargePoints =
        List.of(
            new ChargePointDynStatusResponse(
                "CHARGE_POINT:3ad0a06c-8dae-3451-8acd-267460a9afee",
                OperationalState.AVAILABLE,
                timestamp),
            new ChargePointDynStatusResponse(
                "CHARGE_POINT:5bc6d7e8-9fa0-1b2c-3d4e-5f6a7b8c9d0e",
                OperationalState.CHARGING,
                timestamp),
            new ChargePointDynStatusResponse(
                "CHARGE_POINT:7f8a9b0c-1d2e-3f4a-5b6c-7d8e9f0a1b2c",
                OperationalState.UNKNOWN,
                timestamp));

    chargePoints.forEach(
        cp -> {
          redisTemplate.opsForSet().add("chargepoint:ids", cp.chargePointID());
          redisTemplate.opsForValue().set("chargepoint:" + cp.chargePointID(), cp);
        });

    log.info("✓ Cargados {} charge points en Redis", chargePoints.size());
  }
}
