package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundSubscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FundSubscriptionDbMapper {
    @Mapping(source = "fundDto", target = "fund")
    @Mapping(source = "amount", target = "amount")
    FundSubscription toEntity(FundSubscriptionDto dto);

    @Mapping(source = "fund", target = "fundDto")
    @Mapping(source = "amount", target = "amount")
    FundSubscriptionDto toDto(FundSubscription entity);

    List<FundSubscription> toEntityList(List<FundSubscriptionDto> dtoList);

    List<FundSubscriptionDto> toDtoList(List<FundSubscription> entityList);
}
