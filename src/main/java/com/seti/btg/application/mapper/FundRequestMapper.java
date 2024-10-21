package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.request.FundRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FundRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "minAmount", target = "minAmount")
    @Mapping(source = "category", target = "category")
    Fund toDomain(FundRequest request);
}
