package com.seti.btg.infrastructure.adapter.repository;

import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends JpaRepository<FundEntity, Long> {
    boolean existsByName(String name);
}
