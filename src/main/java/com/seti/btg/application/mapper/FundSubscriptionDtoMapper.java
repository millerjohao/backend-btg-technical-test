package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundSubscriptionDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FundSubscriptionDtoMapper {

    @Mapping(source = "fund.id", target = "fundDto.id")
    @Mapping(source = "fund.name", target = "fundDto.name")
    @Mapping(source = "fund.minAmount", target = "fundDto.minAmount")
    @Mapping(source = "fund.category", target = "fundDto.category")
    @Mapping(source = "amount", target = "amount")
    FundSubscriptionDto toDto(FundSubscription fundSubscription);


    @InheritInverseConfiguration
    FundSubscription toDomain(FundSubscriptionDto fundSubscriptionDto);
}