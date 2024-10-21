package com.seti.btg.infrastructure.adapter.mapper;

import com.seti.btg.domain.model.Customer;
import com.seti.btg.infrastructure.adapter.entity.CustomerEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerDbMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "notificationType", target = "notificationType")
    CustomerEntity toDbo(Customer domain);

    @InheritInverseConfiguration
    Customer toDomain(CustomerEntity entity);
}
