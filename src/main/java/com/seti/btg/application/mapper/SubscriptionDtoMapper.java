package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Subscription;
import com.seti.btg.domain.model.dto.SubscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionDtoMapper {
    @Mapping(source = "idCustomer", target = "idCustomer")
    @Mapping(source = "idFund", target = "idFund")
    @Mapping(source = "amount", target = "amount")
    SubscriptionDto toDto(Subscription domain);
}
