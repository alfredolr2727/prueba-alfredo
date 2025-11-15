package com.jember.alfredo.prueba.repository;

import com.jember.alfredo.prueba.repository.entity.PoolEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository extends JpaRepository<PoolEntity, Long> {

  List<PoolEntity> findByDcsPoolIdIn(List<String> dcsPoolIds);

}