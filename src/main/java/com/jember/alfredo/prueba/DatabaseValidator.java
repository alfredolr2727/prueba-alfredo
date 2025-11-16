package com.jember.alfredo.prueba;

import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseValidator implements CommandLineRunner {

  private final DataSource dataSource;

  public DatabaseValidator(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void run(String... args) {
    try (var conn = dataSource.getConnection()) {
      log.info("✅ DB Connection OK: {}", conn.getMetaData().getURL());
    } catch (SQLException e) {
      log.info("❌ Cannot connect to DB: {}", e.getMessage());
      System.exit(1); // fuerza que la app falle
    }
  }
}
