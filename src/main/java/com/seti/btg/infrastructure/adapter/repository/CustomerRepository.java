package com.seti.btg.infrastructure.adapter.repository;

import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {


    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);


}
