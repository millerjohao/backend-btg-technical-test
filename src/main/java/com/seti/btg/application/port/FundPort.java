package com.seti.btg.application.port;

import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.request.FundRequest;

import java.util.List;

public interface FundPort {
    FundDto createNewFund(FundRequest fund);
    List<FundDto> getAll();
    FundDto getFundById(Long id);
}
