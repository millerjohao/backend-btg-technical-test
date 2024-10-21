package com.seti.btg.application.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.dto.FundDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FundDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "minAmount", target = "minAmount")
    @Mapping(source = "category", target = "category")
    FundDto toDto(Fund domain);
}
