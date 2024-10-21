package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Fund;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FundDbMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "minAmount", target = "minAmount")
    @Mapping(source = "category", target = "category")
    FundEntity toDbo(Fund domain);

    @InheritInverseConfiguration
    Fund toDomain(FundEntity entity);
}
