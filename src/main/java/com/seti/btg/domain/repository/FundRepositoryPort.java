package com.seti.btg.domain.repository;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.FundSubscription;

import java.util.List;


public interface FundRepositoryPort {
    Fund createNewFund(Fund fund);

    List<Fund> getAll();

    Fund getFundById(Long id);

    List<FundSubscription> getFundsByCustomerId(Long customerId);
}
