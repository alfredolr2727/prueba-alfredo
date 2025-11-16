package com.jember.alfredo.prueba;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisValidator implements CommandLineRunner {

  private final RedisConnectionFactory connectionFactory;

  public RedisValidator(RedisConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void run(String... args) {
    try (var conn = connectionFactory.getConnection()) {
      conn.ping();
      log.info("✅ Redis connection OK");
    } catch (Exception e) {
      log.info("❌ Cannot connect to Redis: {}", e.getMessage());
      System.exit(1);
    }
  }
}
