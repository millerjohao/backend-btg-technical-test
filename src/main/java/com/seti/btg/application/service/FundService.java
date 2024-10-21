package com.seti.btg.application.service;

import com.seti.btg.application.mapper.FundDtoMapper;
import com.seti.btg.application.mapper.FundRequestMapper;
import com.seti.btg.application.port.FundPort;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.request.FundRequest;
import com.seti.btg.domain.repository.FundRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundService implements FundPort {


    @Autowired
    private FundRepositoryPort repository;
    @Autowired
    private FundDtoMapper dtoMapper;

    @Autowired
    private FundRequestMapper requestMapper;


    @Override
    public FundDto createNewFund(FundRequest fund) {
        var newFund = requestMapper.toDomain(fund);
        var createdFund = repository.createNewFund(newFund);
        return dtoMapper.toDto(createdFund);
    }

    @Override
    public List<FundDto> getAll() {
        var funds = repository.getAll();
        return funds
                .stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FundDto getFundById(Long id) {
        var fund = repository.getFundById(id);
        return dtoMapper.toDto(fund);
    }
}
