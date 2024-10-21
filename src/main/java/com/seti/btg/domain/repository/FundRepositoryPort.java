package com.seti.btg.domain.repository;

import com.seti.btg.domain.model.Fund;

import java.util.List;


public interface FundRepositoryPort {
    Fund createNewFund(Fund fund);

    List<Fund> getAll();

    Fund getFundById(Long id);
}
