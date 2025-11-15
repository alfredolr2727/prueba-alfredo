package com.jember.alfredo.prueba.repository;

import com.jember.alfredo.prueba.repository.entity.PoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository
    extends JpaRepository<PoolEntity, Long>, JpaSpecificationExecutor<PoolEntity> {}
