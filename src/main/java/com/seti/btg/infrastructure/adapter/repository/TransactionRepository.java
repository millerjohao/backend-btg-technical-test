package com.seti.btg.infrastructure.adapter.repository;

import com.seti.btg.infrastructure.adapter.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

}
